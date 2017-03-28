/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.security.repository;

import com.revature.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author FayeRedd
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
