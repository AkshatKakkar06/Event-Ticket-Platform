package com.project.EventTicket.util;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class JwtUtil {

    public static UUID parseJwt(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }
}
