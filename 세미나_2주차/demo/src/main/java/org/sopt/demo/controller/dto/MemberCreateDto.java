package org.sopt.demo.controller.dto;

import org.sopt.demo.domain.Part;

public record MemberCreateDto(
        String name,
        Part part,
        int age
) {
}