package com.barogo.common.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ResultCode {

    RESULT_0000("0000","성공"),
    RESULT_9999("9999","실패",HttpStatus.INTERNAL_SERVER_ERROR),

    // 토큰 인증오류
    RESULT_4000("4000","잘못 된 요청입니다.", HttpStatus.BAD_REQUEST),
    RESULT_4012("4012", "인증정보 에러",HttpStatus.UNAUTHORIZED),
    RESULT_4003("4003", "비밀번호가 일치하지 않습니다.",HttpStatus.UNAUTHORIZED),

    RESULT_4009("4009","이미 존재하는 사용자입니다.",HttpStatus.CONFLICT),

    RESULT_4010("4010","userId 값이 누락되었습니다.",HttpStatus.BAD_REQUEST),
    RESULT_4011("4011","password 값이 누락되었습니다.",HttpStatus.BAD_REQUEST),

    RESULT_4014("4013", "인증정보가 만료되었습니다.",HttpStatus.UNAUTHORIZED),

    // 회원 검증오류
    RESULT_1001("1001","비밀번호는 공백 미포함, 영문 대문자, 소문자, 숫자, 특수문자 중 최소 3가지 이상 포함 12자 이상이여야합니다.(사용가능 특수문자 : ~₩!@#/\\[\\]$%^&*()_+)"),
    RESULT_1003("1003","비밀번호에 공백문자를 포함할 수 없습니다."),

    RESULT_1006("1006","존재하지 않는 아이디 입니다."),
    RESULT_1007("1007","아이디 또는 비밀번호를 다시 확인해 주세요."),

;


    @Getter
    private final String resultCode;

    @Getter
    private final String resultMessage;

    @Getter
    private final HttpStatus httpStatus;

    ResultCode(String resultCode, String resultMessage) {
        this(resultCode, resultMessage, HttpStatus.OK);
    }

    ResultCode(String resultCode, String resultMessage, HttpStatus httpStatus) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.httpStatus = httpStatus;
    }

}
