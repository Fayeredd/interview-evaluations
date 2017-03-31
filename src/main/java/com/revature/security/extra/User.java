/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.security.extra;

/**
 *
 * @author FayeRedd
 */
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
This is a placeholder Entity for the User POJO. Feel free to modify/edit as needed
for compatibility with actual database Table
*/

@Entity
@Table(name = "ie_user")
public class User {

        @Id
        @Column(name = "u_userID")
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
        @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
        private Long id;

        @Column(name = "u_username", length = 50, unique = true)
        @NotNull
        @Size(min = 4, max = 50)
        private String username;

        @Column(name = "u_password", length = 100)
        @NotNull
        @Size(min = 4, max = 100)
        private String password;

        @Column(name = "u_firstName", length = 50)
        @NotNull
        @Size(min = 4, max = 50)
        private String firstname;

        @Column(name = "u_lastName", length = 50)
        @NotNull
        @Size(min = 4, max = 50)
        private String lastname;

        @Column(name = "u_email", length = 50)
        @NotNull
        @Size(min = 4, max = 50)
        private String email;

        @Column(name = "u_enabled")
        @NotNull
        private Boolean enabled;

        @Column(name = "u_lastpasswordresetdate")
        @Temporal(TemporalType.TIMESTAMP)
        @NotNull
        private Date lastPasswordResetDate;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "ua_user_authority",
                joinColumns = {@JoinColumn(name = "u_user_id", referencedColumnName = "u_userID")},
                inverseJoinColumns = {@JoinColumn(name = "a_authority_id", referencedColumnName = "a_authID")})
        private List<Authority> authorities;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public List<Authority> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(List<Authority> authorities) {
            this.authorities = authorities;
        }

        public Date getLastPasswordResetDate() {
            return lastPasswordResetDate;
        }

        public void setLastPasswordResetDate(Date lastPasswordResetDate) {
            this.lastPasswordResetDate = lastPasswordResetDate;
        }
}
