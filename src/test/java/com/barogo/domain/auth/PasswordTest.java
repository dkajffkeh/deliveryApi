package com.barogo.domain.auth;

import com.barogo.domain.auth.controller.parameter.signup.SignUpParameter;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

class PasswordTest {

    private static final Logger log = LoggerFactory.getLogger(PasswordTest.class);
    private static Validator validator;

    public static final String PROPER_USER_ID = "barogo12345";
    public static final String PROPER_USERNAME = "홍길동";

    public static final String PROPER_PWD = "$Aa1@BarogoStart";

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("회원가입 요청 정상 케이스 테스트")
    void normalCaseTest() {
        final SignUpParameter signUpParameter =
                new SignUpParameter(
                        PROPER_USER_ID,
                        PROPER_PWD,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations = validator.validate(signUpParameter);
        Assertions.assertEquals(constraintViolations.size(),0);
    }

    @Test
    @DisplayName("숫자,대문자,소문자,문자 3개 이상 만족하는 비밀번호 - 정상케이스")
    void normalCaseTestSecond() {
        final String normalCasePwd_1 = "AAbb//dawddw";
        final String normalCasePwd_2 = "AA11//123456";
        final String normalCasePwd_3 = "AAbb//12dawddw";

        final SignUpParameter signUpParameter_1 =
                new SignUpParameter(
                        PROPER_USER_ID,
                        normalCasePwd_1,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations_1 = validator.validate(signUpParameter_1);
        Assertions.assertEquals(constraintViolations_1.size(),0);

        final SignUpParameter signUpParameter_2 =
                new SignUpParameter(
                        PROPER_USER_ID,
                        normalCasePwd_2,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations_2 = validator.validate(signUpParameter_2);
        Assertions.assertEquals(constraintViolations_2.size(),0);

        final SignUpParameter signUpParameter_3 =
                new SignUpParameter(
                        PROPER_USER_ID,
                        normalCasePwd_3,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations_3 = validator.validate(signUpParameter_3);
        Assertions.assertEquals(constraintViolations_3.size(),0);
    }

    @Test
    @DisplayName("숫자,대문자,소문자,문자 3개 이하 만족하는 비밀번호 - 에러케이스")
    void notMatchedTest() {
        final String normalCasePwd_1 = "asdasnmdASDASD";

        final SignUpParameter signUpParameter_1 =
                new SignUpParameter(
                        PROPER_USER_ID,
                        normalCasePwd_1,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations_1 = validator.validate(signUpParameter_1);
        Assertions.assertEquals(constraintViolations_1.size(),1);
    }

    @Test
    @DisplayName("비밀번호 공백 문자열 테스트")
    void hasBlankTest() {
        final String blankContaningPwd = " $Aa1@Ba rogoStart";

        final SignUpParameter signUpParameter =
                new SignUpParameter(
                        PROPER_USER_ID,
                        blankContaningPwd,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations = validator.validate(signUpParameter);
        Assertions.assertEquals(constraintViolations.size(),1);
    }

    @Test
    @DisplayName("최소 길이를 만족하지 못하는 비밀번호 테스트")
    void pwdTooShortTest() {
        final String tooShortPwd = "$Aa1@";

        final SignUpParameter signUpParameter =
                new SignUpParameter(
                        PROPER_USER_ID,
                        tooShortPwd,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations = validator.validate(signUpParameter);
        Assertions.assertEquals(constraintViolations.size(),1);
    }

    @Test
    @DisplayName("적합하지 못한 문자열 사용 케이스 테스트")
    void inValidCharTest() {
        final String pwdWithEmoji = PROPER_PWD + "😂";
        final String pwdWith = PROPER_PWD+"≤";

        final SignUpParameter initEmoji =
                new SignUpParameter(
                        PROPER_USER_ID,
                        pwdWithEmoji,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations = validator.validate(initEmoji);
        Assertions.assertEquals(constraintViolations.size(),1);

        final SignUpParameter initLoe =
                new SignUpParameter(
                        PROPER_USER_ID,
                        pwdWith,
                        PROPER_USERNAME);

        Set<ConstraintViolation<SignUpParameter>> constraintViolations2 = validator.validate(initLoe);
        Assertions.assertEquals(constraintViolations2.size(),1);
    }
}