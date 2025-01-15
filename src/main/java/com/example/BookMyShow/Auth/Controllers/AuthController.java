package com.example.BookMyShow.Auth.Controllers;

import java.net.http.HttpHeaders;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookMyShow.Auth.DTO.UserDto;
import com.example.BookMyShow.Auth.DTO.UserLoginDTO;
import com.example.BookMyShow.Auth.Models.UserModel;
import com.example.BookMyShow.Auth.Services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.BookMyShow.Auth.Services.*;

@RestController
public class AuthController {

	UserService UserService;
	JWTGenerator JWTGenerator;
	
	@Autowired
	public AuthController(UserService UserService, JWTGenerator JWTGenerator) {
		this.UserService = UserService;
		this.JWTGenerator = JWTGenerator;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(HttpServletResponse response,
			@RequestBody UserLoginDTO data) throws NoSuchAlgorithmException {
		String token = null;
		Map<String, String> re = new HashMap<>();
		try {
			token = UserService.Login(data);
			Cookie cookie = new Cookie("Bearer", token);
			cookie.setHttpOnly(true); // Prevent JavaScript access
	        cookie.setSecure(true); // Use true if you're on HTTPS
	        cookie.setPath("/"); // Set the cookie path
	        cookie.setMaxAge(2* 60 * 60); // Set cookie expiration (1 day)
			System.out.println("came her");
			re.put("Token Status", "Generated");
			response.addCookie(cookie);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(re);
		} catch (Exception ex) {

	        re.put("error", "INVALID_CREDENTIALS");
	        re.put("message", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
		}

		

	}

	@PostMapping("/SignUp")
	public ResponseEntity<Map<String, String>> other(@RequestBody UserDto data) {
		Map<String, String> re = new HashMap<>();
		try {
		 UserService.CreateNewUser(data);
		 re.put(data.getEmailid(), "Added SuccessFully");
		 return ResponseEntity.status(HttpStatus.ACCEPTED).body(re); }
		
		catch(Exception e) {
			re.put(data.getEmailid(),"User Lready Present");
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
			
		}
		
	}

	@PostMapping("/auth")
	public String auth() {
		System.out.println("came all the way to here");
		return "What man What are you expecting";

	}
	
	@PostMapping("/auth1")
	public String auth1() {
		System.out.println("came all the way to here");
		return "What man What are you expecting";

	}

}
