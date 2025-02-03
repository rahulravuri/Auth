package com.example.BookMyShow.Auth.Security;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Base64;

import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.BookMyShow.Auth.Util.KeyUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class AuthFilters extends  OncePerRequestFilter {
	
	@Autowired
    KeyUtil KeyUtil;
	@Autowired
	com.example.BookMyShow.Auth.Services.CustomUserDetailService CustomUserDetailService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("hellowdsfd there");
		 String path = request.getRequestURI();
		 
	        if (path.equals("/login") || path.equals("/SignUp")  || path.equals("/auth1")) {
	            filterChain.doFilter(request, response); // Proceed without filtering
	            
	        }
	        else {
	        	Cookie[] cookies = request.getCookies();
	        	Claims claims=null;
	        	for(Cookie i:cookies) {
	        		if(i.getName().equals("Bearer")) {
	        			PublicKey PublicKey;
						try {
							PublicKey = KeyUtil.getPublicKey("publicKey.pem");
							claims=Jwts.parser()
		        		            .verifyWith(PublicKey)
		        	                .build()
		        	                .parseSignedClaims(i.getValue())
		        	                .getPayload();
						} catch (Exception e) {
                            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
							((HttpServletResponse) response).setContentType("application/json");
							((HttpServletResponse) response).getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
							return ;
						}
	        			
	        		}
	        	}
	        		if(claims==null) {
						((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
						((HttpServletResponse) response).setContentType("application/json");
						((HttpServletResponse) response).getWriter().write("{\"error\": \"" +"No Token Please Login" + "\"}");
						return ;
	        		
	        	}
	        		String username = (String) claims.get("user");

					System.out.println(username);
					try {
						User userDetails = CustomUserDetailService.loadUserByUsername(username);
						var authToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authToken);
						HttpSession session = request.getSession();
						session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

						System.out.println(SecurityContextHolder.getContext().getAuthentication());

						filterChain.doFilter(request, response);
					} catch (Exception e) {
						((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
						((HttpServletResponse) response).setContentType("application/json");
						((HttpServletResponse) response).getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
						return ;
					}


	        }		
	}

}

class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
