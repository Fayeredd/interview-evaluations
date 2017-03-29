/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.security.controllers;

import com.revature.security.JwtAuthenticationRequest;
import com.revature.security.JwtTokenUtil;
import com.revature.security.JwtUser;
import com.revature.security.service.JwtAuthenticationResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author FayeRedd
 */

@RestController
public class AuthenticationRestController {
    
    //This value can be found in the application.yml file
    @Value("${jwt.header}")
    private String tokenHeader;
    
    @Autowired
    private AuthenticationManager authMan;
    
    //This is the token generator/validator. Can be replaced with future microservice providing/varifying tokens
    @Autowired
    private JwtTokenUtil tokenUtil;
    
    @Autowired
    private UserDetailsService userDetServ;
    
    //This value can be found in the application.yml file
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authReq, Device dev) throws AuthenticationException{
        
        final Authentication authentication = authMan.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authReq.getUsername(),
                    authReq.getPassword())
            );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        final UserDetails userDetails = userDetServ.loadUserByUsername(authReq.getUsername());
        final String token = tokenUtil.generateToken(userDetails, dev);
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
    
    //This value can be found in the application.yml file
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest req){
        String token = req.getHeader(tokenHeader);
        String username = tokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetServ.loadUserByUsername(username);
        
        if(tokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            String refreshedToken = tokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }    
}
