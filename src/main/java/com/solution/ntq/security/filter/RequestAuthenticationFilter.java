package com.solution.ntq.security.filter;


import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.service.base.TokenService;
import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;

/**
 * @author Nam Phuong
 * @since 15/Aug/2109
 */

@CrossOrigin
public class RequestAuthenticationFilter implements Filter {
    private TokenService tokenService;
    private Environment env;

    public RequestAuthenticationFilter(TokenService tokenService, Environment env) {
        this.tokenService = tokenService;
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                Authentication authentication = request.getHeader("id_token") == null ? null : getAuthentication((HttpServletRequest) servletRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (InvalidRequestException ex) {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }

        }
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String idToken = request.getHeader("id_token");
        try {
            String userId = getUserIdFormJWT(idToken);
            if (userId == null) {
                return null;
            }
            Token token = tokenService.getTokenByUserId(userId);
            if (!userId.equals(token.getUser().getId()) || tokenService.isTimeOut(token)) {
                throw new InvalidRequestException("Token time out");
            }
            token.setTime(new Date());
            tokenService.save(token);
            request.setAttribute("userId", userId);
            return new UsernamePasswordAuthenticationToken(userId, null, emptyList());

        } catch (InvalidRequestException ex) {
            throw new InvalidRequestException("Token error");
        } catch (IOException ex) {
            throw new InvalidRequestException(ex.getMessage());
        }
    }

    private String getUserIdFormJWT(String token) {
        String jwtTokenPrefixSetup = env.getProperty("JWT_TOKEN_PREFIX");
        return Jwts.parser().setSigningKey(env.getProperty("JWT_SECRET"))
                .parseClaimsJws(token.replace(jwtTokenPrefixSetup, ""))
                .getBody()
                .getSubject();
    }
}

