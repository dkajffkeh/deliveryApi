package com.barogo.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.barogo.domain.auth.Password.isValidPwdForm;

public class PasswordValidator implements ConstraintValidator<ValidPwd,String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return isValidPwdForm(password);
    }
}
