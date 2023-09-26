package com.basinda.filters;

import com.basinda.exceptions.ApiError;
import com.basinda.exceptions.GlobalExceptionHandler;
import com.basinda.models.response.ResponseHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import com.basinda.contants.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.basinda.exceptions.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public class Response extends ResponseHeader {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, AuthorizationException {

        String authHeader = request.getHeader("Authorization");
        if(authHeader==null){
            SecurityContextHolder.clearContext();
            authorizationException(HttpStatus.BAD_REQUEST, response, "Unauthorized User.");
        }
        else{
            String token = authHeader.substring(7);
            if (token != null){
                try{
                    SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                    Claims claims = Jwts.parser()
                            .setSigningKey(key).parseClaimsJws(token).getBody();
                    String username = claims.get("username").toString();
                    String role = claims.get("authorities").toString();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username,null, AuthorityUtils.commaSeparatedStringToAuthorityList(role));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request,response);
                }
                catch (Exception ex){
                    SecurityContextHolder.clearContext();
                    authorizationException(HttpStatus.BAD_REQUEST, response, "Unauthorized User.");
                }
            }
            else{
                SecurityContextHolder.clearContext();
                authorizationException(HttpStatus.BAD_REQUEST, response, "Unauthorized User.");
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean ok = request.getServletPath().equals("/auth/login");
        return ok;
    }

    public void authorizationException(HttpStatus status, HttpServletResponse response, String message){
        response.setStatus(status.value());
        response.setContentType("application/json");
        ApiError apiError = new ApiError(status, message);
        try {
            String json = apiError.convertToJson();
            response.getWriter().write(json);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}