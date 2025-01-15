package com.example.BookMyShow.Auth.Util;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;



@Component
public class KeyUtil {
	
	 public static  KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		 System.out.println("dsfssdfkdsbfksbdfkjbdsfjbddf");
	        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	        keyGen.initialize(2048); // or 4096
	        KeyPair keyPair = keyGen.generateKeyPair();
	       	return keyPair;
	    }

    public  PrivateKey getPrivateKey(String filename) throws Exception {
    	ClassPathResource resource = new ClassPathResource(filename);
        InputStream inputStream = resource.getInputStream();
        
        byte[] keyBytes = inputStream.readAllBytes();
        String key = new String(keyBytes);
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", ""); // Remove all whitespaces
        	byte[] decoded = Base64.getDecoder().decode(key);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
    }

    public  PublicKey getPublicKey(String filename) throws Exception {
    	ClassPathResource resource = new ClassPathResource(filename);
        InputStream inputStream = resource.getInputStream();
        
        byte[] keyBytes = inputStream.readAllBytes();
        String publicKeyPEM = new String(keyBytes);
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""); // Remove all whitespaces
        		byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
    }
    
    public static  Claims parseJwt(String jwtToken, PublicKey PublicKey) {
	    // You need to pass the public/private key depending on your JWT signing mechanism
	    return Jwts.parser()
	            .verifyWith(PublicKey)
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
	}
}
