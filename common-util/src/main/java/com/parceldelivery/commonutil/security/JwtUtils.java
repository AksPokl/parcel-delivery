package com.parceldelivery.commonutil.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.parceldelivery.commonutil.security.SecurityConstants.AUTHORIZATION;
import static com.parceldelivery.commonutil.security.SecurityConstants.AUTHORIZATION_INDEX;
import static com.parceldelivery.commonutil.security.SecurityConstants.BEARER;
import static com.parceldelivery.commonutil.security.SecurityConstants.REQUEST_AUTHORIZATION;
import static com.parceldelivery.commonutil.security.SecurityConstants.ROLES;
import static com.parceldelivery.commonutil.security.SecurityConstants.USERNAME;
import static com.parceldelivery.commonutil.security.SecurityConstants.USER_ID;

@UtilityClass
public class JwtUtils {

    public String getUserNameFromJwtToken(String token, String secret) {
        Claims claims = parseJwt(token, secret);
        if (claims != null && claims.containsKey(USERNAME)) {
            return String.valueOf(claims.get(USERNAME));
        }

        throw new RuntimeException("It's impossible to parse username from token");
    }

    public String getUserIdFromJwtToken(String token, String secret) {
        Claims claims = parseJwt(token, secret);
        if (claims != null && claims.containsKey(USER_ID)) {
            return String.valueOf(claims.get(USER_ID));
        }

        throw new RuntimeException("It's impossible to parse user id from token");
    }

    public Pair<String, List<String>> getUserNameAndRolesFromJwtToken(String token, String secret) {
        Claims claims = parseJwt(token, secret);
        if (claims != null && claims.containsKey(USERNAME) && claims.containsKey(ROLES)) {
            return Pair.of(String.valueOf(claims.get(USERNAME)), parseRoles(claims));
        }

        throw new RuntimeException("It's impossible to parse username from token");
    }

    private List<String> parseRoles(Claims claims) {
        List<LinkedHashMap<String, String>> roles = (List<LinkedHashMap<String, String>>) claims.get(ROLES);
        return roles.stream()
                .map(LinkedHashMap::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public Claims parseJwt(String jwt, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encode(secret.getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean validateJwtToken(String authToken, String secret) {
        if (!StringUtils.hasText(authToken)) {
            return false;
        }
        Claims claims = parseJwt(authToken, secret);
        return claims != null && !claims.isEmpty();
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);

        return parseToken(headerAuth);
    }

    public String parseToken(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getFirst(REQUEST_AUTHORIZATION);

        return parseToken(headerAuth);
    }

    public String parseToken(String headerAuth) {
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER)) {
            return headerAuth.substring(AUTHORIZATION_INDEX);
        }

        return null;
    }
}
