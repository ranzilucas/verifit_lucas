package com.ranzi.verifit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class MemberDto implements Serializable {


    private Long membershipId;

    private String name;

    private int currentStreak;

    private boolean discount;



}
