package org.sopt.demo.controller;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.sopt.demo.controller.dto.MemberCreateDto;
import org.sopt.demo.repository.MemberRepository;
import org.sopt.demo.service.MemberService;
import org.sopt.demo.settings.ApiTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.awt.*;

import static org.sopt.demo.domain.Part.SERVER;

public class MemberControllerTest extends ApiTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("멤버 생성 테스트")
    public class CreateMember{

        @Test
        @DisplayName("요청 성공 케이스")
        public void createMemberSuccess() throws Exception{
            //given
            final var request = new MemberCreateDto(
                   "황혜린",
                   SERVER,
                   26
            );
            //when
            final var response = RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("/api/v1/member")
                    .then().log().all().extract();
            //then
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        }
    }
}
