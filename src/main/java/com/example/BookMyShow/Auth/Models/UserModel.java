package com.example.BookMyShow.Auth.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	private String password;
	private int phone_number;
	private String emailid;
    private UserAccess UserAccess;

     public void setUserAccess(UserAccess UserAccess){
        this.UserAccess=UserAccess;
    }
    public UserAccess getUserAccess( ){
        return this.UserAccess;
    }


    public String getEmailid() {
        return emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }
}
