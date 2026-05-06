package com.sy.backendassignment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    INVALID_ITEM(HttpStatus.BAD_REQUEST, "유효하지 않은 상품입니다."),

    PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "결제에 실패했습니다.");

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus httpstatus, String message) {
        this.httpStatus = httpstatus;
        this.message = message;
    }

}
