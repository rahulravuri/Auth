package com.example.BookMyShow.Auth.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.BookMyShow.Auth.Models.UserModel;
import com.example.BookMyShow.Auth.Repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	

	@Autowired
	 private UserRepository UserRepository;

	@Override
	public User loadUserByUsername(String username) {
		Optional<UserModel> optionalUser = UserRepository.findByEmailid(username);
        
        // Handle case where the user is not found
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        // User found, proceed with adding roles
        UserModel user = optionalUser.get();
		 List<GrantedAuthority> authorities = new ArrayList<>();
		 authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	
	return new org.springframework.security.core.userdetails.User(
            user.getEmailid(),
            "{bcrypt}"+user.getPassword(), 
            authorities
    );
	}

}
