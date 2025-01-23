package com.example.BookMyShow.Auth.Services;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.BookMyShow.Auth.Models.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.KeyPair;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JWTGenerator {

	@Autowired
    private PrivateKey PrivateKey;
	@Autowired
	private PublicKey PublicKey;
	
	@Value("${jwt.expiration-time}")
	private Long expirationTime;
	
	public String genJWT(UserModel um) throws NoSuchAlgorithmException {
			Map<String,Object> jwtData = new HashMap<>();
	        jwtData.put("user",um.getEmailid());
	        jwtData.put("User_id",um.getUserid());
	        jwtData.put("role",um.getUserAccess());
	        long nowInMillis = System.currentTimeMillis();
	        jwtData.put("expiryTime",new Date(nowInMillis+expirationTime));
	        jwtData.put("createdAt",new Date(nowInMillis));
	        String token = Jwts.builder()
	        		.claims(jwtData).signWith(PrivateKey).compact();
	       return token;
	}
	
	
	 

}