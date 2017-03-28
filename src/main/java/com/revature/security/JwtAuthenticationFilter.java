/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author FayeRedd
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Value("Authorization")
    private String tokenHeader;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        
        logger.info("checking authentication for das user " + username);
        
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            if(jwtTokenUtil.validateToken(authToken, userDetails)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                logger.info("authenticated user " + username + ", setting security context");
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
