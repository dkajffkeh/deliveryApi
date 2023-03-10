package com.barogo.common.type;


import com.barogo.common.code.ResultCode;
import com.barogo.common.exception.ApiServerException;
import lombok.Getter;

public enum Role {

    ROLE_ADMIN("ROLE_ADMIN","ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER","MANAGER"),
    ROLE_USER("ROLE_USER","USER")
    ;

    @Getter
    private final String role;

    @Getter
    private final String securityRoleValue;


    Role(String role, String securityRoleValue) {
        this.role = role;
        this.securityRoleValue = securityRoleValue;
    }

    public static Role getRoleTypeByString(String role){
        switch (role){
            case "ROLE_ADMIN" :return ROLE_ADMIN;
            case "ROLE_MANAGER" : return ROLE_MANAGER;
            case "ROLE_USER" : return ROLE_USER;
            default: throw new ApiServerException(ResultCode.RESULT_4000);
        }
    }
}