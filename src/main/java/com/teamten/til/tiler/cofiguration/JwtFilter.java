package com.teamten.til.tiler.cofiguration;

import com.teamten.til.tiler.service.TilerService;
import com.teamten.til.tiler.utils.JwtTokenUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final TilerService tilerService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // authorization check
        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.error("authorization is wrong");
            filterChain.doFilter(request, response);
            return;
        }
        // token 꺼내기
        String token = authorization.split(" ")[1];
        // token 만료 체크
        if(!JwtTokenUtil.isExpired(token, secretKey)){
            log.error("toen is Expired");
            filterChain.doFilter(request, response);
            return ;
        }

        String email = JwtTokenUtil.getEmail(token, secretKey);


        //권한부여
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("TIL_EMAIL")));
        //contents
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
