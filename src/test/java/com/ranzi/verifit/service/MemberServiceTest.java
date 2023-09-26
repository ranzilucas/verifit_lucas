package com.ranzi.verifit.service;

import com.ranzi.verifit.dto.MemberDto;
import com.ranzi.verifit.exception.InternalServerException;
import com.ranzi.verifit.exception.NotFoundException;
import com.ranzi.verifit.model.Member;
import com.ranzi.verifit.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService service;

    @Mock
    private MemberRepository memberRepository;

    private static final Long memberId = 1L;

    @Test
    void givenRepositoryMocked_whenUpdateAttendance_shouldCallRepositorySave() {
        var member = createActiveMember();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        service.updateAttendance(memberId);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void givenMemberNotFound_whenUpdateAttendance_shouldReturnNotFoundException() {
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.updateAttendance(memberId));

        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void givenRepositoryMocked_whenGetGymAttendance_shouldReturnMemberDto() {
        var member = createActiveMember();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        MemberDto memberDto = service.getGymAttendance(memberId);

        assertEquals(member.getMembershipId(), memberDto.getMembershipId());
        assertEquals(member.getName(), memberDto.getName());
        assertEquals(member.getCurrentStreak(), memberDto.getCurrentStreak());
        assertEquals(member.isDiscountStatus(), memberDto.isDiscount());
    }

    @Test
    void givenMemberNotFound_whenGetGymAttendance_shouldReturnNotFoundException() {
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getGymAttendance(memberId));
    }


    @Test
    void givenMemberWithMore3Streak_whenIsDiscounted_shouldReturnTrue() {
        var member = createActiveMember();
        member.setDiscountStatus(false);
        member.setCurrentStreak(4);
        Boolean discounted = service.isDiscounted(member);

        assertTrue(discounted);

        verifyNoInteractions(memberRepository);
    }

    @Test
    void givenMemberWithEqual3Streak_whenIsDiscounted_shouldReturnTrue() {
        var member = createActiveMember();
        member.setDiscountStatus(false);
        member.setCurrentStreak(3);
        Boolean discounted = service.isDiscounted(member);

        assertTrue(discounted);

        verifyNoInteractions(memberRepository);
    }

    @Test
    void givenMemberWithLess3Streak_whenIsDiscounted_shouldReturnFalse() {
        var member = createActiveMember();
        member.setDiscountStatus(false);
        member.setCurrentStreak(2);
        Boolean discounted = service.isDiscounted(member);

        assertFalse(discounted);

        verifyNoInteractions(memberRepository);
    }

    @Test
    void givenMemberAlreadyWithDiscount_whenIsDiscounted_shouldReturnTrue() {
        var member = createActiveMember();
        member.setDiscountStatus(true);
        Boolean discounted = service.isDiscounted(member);

        assertTrue(discounted);

        verifyNoInteractions(memberRepository);
    }


    @Test
    void givenActiveMember_whenCalculateStreak_shouldReturn3() {
        var member = new Member();
        member.setLastAttendance(LocalDate.now().minusDays(7));
        member.setCurrentStreak(2);

        Integer streak = service.calculateStreak(member);

        assertEquals(3, streak);

        verifyNoInteractions(memberRepository);
    }

    @Test
    void givenMemberLastAttendance2WeeksAgo_whenCalculateStreak_shouldReturn1() {
        var member = new Member();
        member.setLastAttendance(LocalDate.now().minusDays(14));
        member.setCurrentStreak(2);

        Integer streak = service.calculateStreak(member);

        assertEquals(1, streak);

        verifyNoInteractions(memberRepository);
    }

    @Test
    void givenMemberAlreadyAttendanceToday_whenCalculateStreak_shouldReturnError() {
        var member = new Member();
        member.setLastAttendance(LocalDate.now());
        member.setCurrentStreak(2);

        assertThrows(InternalServerException.class, () -> service.calculateStreak(member));

        verifyNoInteractions(memberRepository);
    }

    private Member createActiveMember() {
        Member member = new Member();
        member.setMembershipId(1L);
        member.setLastAttendance(LocalDate.now().minusDays(7));
        member.setCurrentStreak(2);
        member.setDiscountStatus(false);
        member.setName("TEST");
        return member;
    }


}