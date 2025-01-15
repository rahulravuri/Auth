package com.example.BookMyShow.Auth.Services;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.example.BookMyShow.Auth.Models.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BookMyShow.Auth.DTO.UserDto;
import com.example.BookMyShow.Auth.DTO.UserLoginDTO;
import com.example.BookMyShow.Auth.Exceptions.UserExceptions;
import com.example.BookMyShow.Auth.Models.UserModel;
import com.example.BookMyShow.Auth.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	 private UserRepository UserRepository;
	@Autowired
	private JWTGenerator JWTGenerator;
	 
	 @Autowired
	    private BCryptPasswordEncoder bCryptPasswordEncoder;


	    public UserModel getUserDetails(Integer id) {
	        return UserRepository.findById(id).get();
	    }
	    
	    public Optional<UserModel> getUserDetailsbyemail(String data) {
	    	return  UserRepository.findByEmailid(data);

	    }
	    
	    public String Login(UserLoginDTO data) throws NoSuchAlgorithmException, UserExceptions {
	    	Optional<UserModel> um=getUserDetailsbyemail(data.getEmailid());
	    	if(um.isEmpty()) {
	    		throw new UserExceptions("User Not Found");
	    	}
	    	boolean match=bCryptPasswordEncoder.matches(data.getPassword(), um.get().getPassword());
				if(!match) {
				
					throw new UserExceptions("Wrong Credentials");
					
				}
				return JWTGenerator.genJWT(um.get());
	    	
	    }
	    
	    private String saveUserDetails(UserDto UserDto) {
	    	UserModel temp=new UserModel();
	    	temp.setEmailid(UserDto.getEmailid());
	    	temp.setPassword(bCryptPasswordEncoder.encode(UserDto.getPassword()));
	    	temp.setPhone_number(UserDto.getPhno());
			temp.setUserAccess(UserAccess.User);
	    	
	        UserRepository.save(temp);
	        return "Done";
	    }
	    
	    public String CreateNewUser(UserDto UserDto) throws UserExceptions {
	    	
	    	Optional<UserModel> um=getUserDetailsbyemail(UserDto.getEmailid());
	    	if(um.isEmpty()) {
	    		saveUserDetails(UserDto);
	    		return "Done";
	    	} 	
	    	throw new UserExceptions("User already Present");
	    	
	    }
	    

}
