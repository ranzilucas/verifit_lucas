package com.ranzi.verifit.service;

import com.ranzi.verifit.dto.MemberDto;
import com.ranzi.verifit.exception.InternalServerException;
import com.ranzi.verifit.exception.NotFoundException;
import com.ranzi.verifit.model.Member;
import com.ranzi.verifit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member updateAttendance(Long memberId) {

        log.info("Updating attendance {}", memberId);

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member member = optionalMember.orElseThrow(() -> new NotFoundException("Membership was not found"));

        member.setCurrentStreak(calculateStreak(member));
        member.setDiscountStatus(isDiscounted(member));
        member.setLastAttendance(LocalDate.now());

        return memberRepository.save(member);
    }

    protected Boolean isDiscounted(Member member) {
        if (member.getCurrentStreak() >= 3) {
            log.info("discounted because streak! ");
            return true;
        } else {
            return member.isDiscountStatus();
        }
    }

    protected int calculateStreak(Member member) {
        LocalDate today = LocalDate.now();
        if (today.isAfter(member.getLastAttendance())) {
            LocalDate lastDate = member.getLastAttendance();
            var baseForWeek = 7 - lastDate.getDayOfWeek().getValue();
            var beginNextWeek = lastDate.plusDays(baseForWeek);
            var endNextWeek = beginNextWeek.plusDays(7);

            if (today.isAfter(beginNextWeek) && today.isBefore(endNextWeek)) {
                log.info("Streak increasing memberId:{}", member.getMembershipId());
                return member.getCurrentStreak() + 1;
            } else {
                log.info("Streak reset member didn't come frequently memberId:{}", member.getMembershipId());
                return 1;
            }
        } else {
            //Same day
            log.error("Member is coming more times same day! {}", member.getMembershipId());
            throw new InternalServerException("You come twice!");
        }
    }

    public MemberDto getGymAttendance(Long memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member member = optionalMember.orElseThrow(() -> new NotFoundException("Membership was not found"));

        return MemberDto.builder()
                .membershipId(member.getMembershipId())
                .currentStreak(member.getCurrentStreak())
                .discount(member.isDiscountStatus())
                .name(member.getName())
                .build();
    }
}