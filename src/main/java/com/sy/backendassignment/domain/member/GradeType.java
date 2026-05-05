package com.sy.backendassignment.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GradeType {
    NORMAL("일반 회원"),
    VIP("우수 회원"),
    VVIP("최우수 회원");

    private final String description;
}
