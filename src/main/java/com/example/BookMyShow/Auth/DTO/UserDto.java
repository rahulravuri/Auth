package com.example.BookMyShow.Auth.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private String password;
	private int phno;
	private String emailid;

	public String getEmailid() {
		return emailid;
	}

	public String getPassword() {
		return password;
	}

	public int getPhno() {
		return phno;
	}
}
