package org.milan.naucnacentrala.security.auth;

import org.milan.naucnacentrala.security.TokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private TokenUtils tokenUtils;

    private CustomUserDetailsService userDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, CustomUserDetailsService userService) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String email;
        //izvuci token iz request-a
        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            // uzmi email iz tokena
            email = tokenUtils.getUsernameFromToken(authToken);

            if (email != null) {
                // uzmi user-a na osnovu emaila-a
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // proveri da li je prosledjeni token validan
                if (tokenUtils.validateToken(authToken, userDetails)) {

                    // kreiraj autentifikaciju
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
