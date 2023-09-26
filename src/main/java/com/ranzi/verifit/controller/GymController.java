package com.ranzi.verifit.controller;

import com.ranzi.verifit.dto.MemberDto;
import com.ranzi.verifit.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;


@Slf4j
@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@Validated
public class GymController {

    private final MemberService service;

    @ApiOperation(code = 201, value = "Method will create a attendance for the member")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "insert new attendance")})
    @PostMapping("member/{memberId}/attendance")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void attendance(@Positive @PathVariable Long memberId) {
        log.info("create new attendance for the memberDto");

        service.updateAttendance(memberId);

        log.info("created attendance");
    }

    @ApiOperation(value = "Allow the gym owner to check if a user should get a discount and user’s current streak", response = MemberDto.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get member info", response = MemberDto.class)})
    @GetMapping("member/{memberId}")
    public ResponseEntity<MemberDto> getAttendance(@Positive @PathVariable Long memberId) {
        log.info("create new attendance for the user");

        var attendance = service.getGymAttendance(memberId);

        log.info("get attendance by user id, {}", memberId);

        return ResponseEntity.ok(attendance);
    }

}
