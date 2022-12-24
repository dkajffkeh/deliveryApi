package com.barogo.common.regex;

import static java.util.regex.Pattern.compile;

public class GlobalRegExContainer {
    public static final String UPPERCASE_REGEX = "[A-Z]";
    public static final String LOWERCASE_REGEX = "[a-z]";
    public static final String NUMBER_REGEX = "[\\d]";
    public static final String BLANK_REGEX = "[\\s]";
    public static final String VALID_SPECIAL_CHAR_REGEX = "[/!@#\\[\\]$%^~&*₩(),.?\":{}_+|<>]";
    public static final String INVALID_CHAR_REGEX = "[^a-zA-Z0-9/!@#\\[\\]$%^~&*₩(),.?\":{}_+|<>]";

    public static boolean hasBlank(String value) {
        return compile(BLANK_REGEX).matcher(value).find();
    }

    public static boolean hasNumber(String value) {
        return compile(NUMBER_REGEX).matcher(value).find();
    }

    public static boolean hasLowerCase(String value) {
        return compile(LOWERCASE_REGEX).matcher(value).find();
    }

    public static boolean hasSpecialChar(String value) {
        return compile(VALID_SPECIAL_CHAR_REGEX).matcher(value).find();
    }

    public static boolean hasUpperCase(String value) {
        return compile(UPPERCASE_REGEX).matcher(value).find();
    }

    public static boolean hasInvalidChar(String value) {
        return compile(INVALID_CHAR_REGEX).matcher(value).find();
    }
}

