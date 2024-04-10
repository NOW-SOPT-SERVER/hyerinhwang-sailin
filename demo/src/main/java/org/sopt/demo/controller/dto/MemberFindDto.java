package org.sopt.demo.controller.dto;

import lombok.Builder;
import org.sopt.demo.domain.Member;
import org.sopt.demo.domain.Part;

@Builder
public record MemberFindDto(
        String name,
        Part part,
        int age
) {
    public static MemberFindDto of(Member member){
        return MemberFindDto.builder()
                .name(member.getName()).part(member.getPart()).age(member.getAge()).build();
    }
}
