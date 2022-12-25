package com.barogo.common.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.barogo.common.code.ResultCode;
import com.barogo.common.exception.ApiServerException;
import com.barogo.common.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtClaimUtil {

    public static String extractClaimByKey(DecodedJWT decodedJWT, String key){
        return decodedJWT
                .getClaim(key)
                .asString();
    }

    public static DecodedJWT extractDecodedToken(String token, JwtProperties jwtProperties){
        try{
        return JWT.require(Algorithm.HMAC512(jwtProperties.getAlgorithm()))
                .build()
                .verify(token);
        } catch (TokenExpiredException exception){
            log.info("[TOKEN EXPIRED] {}", exception.getMessage());
            return null;
        }
    }
}
