/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.security.core.exceptions;

/**
 *
 * @author FayeRedd
 */
public class JwtTokenMissingException extends RuntimeException{
    
    public JwtTokenMissingException(){}
    
    public JwtTokenMissingException(String message){
        super(message);
    }
}
