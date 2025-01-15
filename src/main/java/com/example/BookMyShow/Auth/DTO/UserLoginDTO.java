package com.example.BookMyShow.Auth.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
	private String password;
	private String emailid;

	public String getEmailid() {
		return emailid;
	}

	public String getPassword() {
		return password;
	}
}
