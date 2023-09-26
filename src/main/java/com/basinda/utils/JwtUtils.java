package com.basinda.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import com.basinda.contants.SecurityConstants;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;
import java.util.Collection;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils {

    public static String generateToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer("Teamwebsoft").setSubject(authentication.getName())
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date().getTime()) + 30000000))
                    .signWith(key).compact();
            return jwt;
        }
        else{
            return "";
        }
    }

    public static boolean validateToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public static String getUsernameFromToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority > authorities) {
        Set<String> authority = new HashSet<>();
        for (GrantedAuthority grantedAuthority: authorities){
            authority.add(grantedAuthority.getAuthority());
        }
        return String.join(",",authority);
    }
}