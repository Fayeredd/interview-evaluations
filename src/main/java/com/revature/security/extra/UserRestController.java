/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.security.extra;

import com.revature.security.extra.JwtTokenUtil;
import com.revature.security.core.JwtUser;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author FayeRedd
 */

@RestController
public class UserRestController {
    
    //This value is found in the application.yml file
    @Value("${jwt.header}")
    private String tokenHeader;
    
    //This is the token generator/validator. Can be replaced with future microservice providing/varifying tokens
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @RequestMapping(value="user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest req){
        
        String token = req.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        
        return user;
    }
}