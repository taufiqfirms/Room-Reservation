package com.kelompokdua.booking.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kelompokdua.booking.model.JwtClaim;
import com.kelompokdua.booking.service.UserCredentialService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserCredentialService userCredentialService;
// akan dijalankan setiap kali ada request dari client
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                try {
                    String token = parseJwt(request);
                    if (token != null && jwtUtils.verifyJwtToken(token)) {
                        JwtClaim userInfo = jwtUtils.getUserInfoByToken(token);
                        UserDetails userDetails = userCredentialService.loadByUserId(userInfo.getUserId());
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                        // simpan sesi user ke db security context holder
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (Exception e) {
                    log.error("Cannot set user Authentication : {}", e.getMessage());
                }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
    
}
