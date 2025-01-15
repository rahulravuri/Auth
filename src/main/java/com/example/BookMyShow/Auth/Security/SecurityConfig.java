package com.example.BookMyShow.Auth.Security;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.example.BookMyShow.Auth.Util.KeyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    
    @Autowired
    AuthFilters AuthFilters;
    @Autowired
    KeyUtil KeyUtil;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
        .formLogin(AbstractHttpConfigurer::disable)     // Disable form login
        .httpBasic(AbstractHttpConfigurer::disable)     // Disable basic HTTP authentication
        .logout(AbstractHttpConfigurer::disable)        // Disable logout functionality
        .sessionManagement(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorize ->  	authorize        		
        		.requestMatchers( "/login", "/SignUp").permitAll()
        		//.requestMatchers("/auth").hasAuthority("ROLE_ADMIN")
        		.anyRequest().authenticated()
        		)
        		.addFilterBefore(AuthFilters,UsernamePasswordAuthenticationFilter.class)
        		;
        return httpSecurity.build();
    }
	
	 @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 @Bean
	    public PrivateKey privateKey() throws Exception {
			return KeyUtil.getPrivateKey("privateKey.pem");
	    }

	    @Bean
	    public PublicKey publicKey() throws Exception {
	        return  KeyUtil.getPublicKey("publicKey.pem");
	    }
	    
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

}
