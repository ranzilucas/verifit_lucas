package com.ranzi.verifit.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long membershipId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate lastAttendance;

    @Column
    private int currentStreak;

    @Column
    private boolean discountStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return membershipId != null && Objects.equals(membershipId, member.membershipId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
