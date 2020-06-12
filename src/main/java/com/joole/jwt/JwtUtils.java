package com.joole.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class JwtUtils {

	private static final Key secretKey = MacProvider.generateKey(SignatureAlgorithm.HS512);

	@Value("${jwt.issuer}")
	private String issuer;

	// 60 minutes in milliseconds
	private final int expirationTime = 3600000;

	@Value("${jwt.subject}")
	private String subject;

	public String generateToken(String subject) {

		JwtBuilder jtoken;

		final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

		//System.out.println(signatureAlgorithm);
	
		Date creationDateTime = new Date(System.currentTimeMillis());

		//System.out.println("The time is :" + creationDateTime);

		Date expDateTime = new Date(System.currentTimeMillis() + expirationTime);

		//System.out.println("The exp time is :" + expDateTime);

		jtoken = Jwts.builder().setIssuedAt(creationDateTime).setSubject(subject).setIssuer(issuer)
				.signWith(signatureAlgorithm, secretKey).setExpiration(expDateTime);

		return jtoken.compact();
	}

	public Claims decodeJsonWebToken(String token) throws SignatureException,MalformedJwtException {

		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

		return claims;
	}

	public boolean validateToken(String bearerToken, String userSubject) {
		String userHeader = this.decodeJsonWebToken(bearerToken).getSubject();
		Date expHeader = this.decodeJsonWebToken(bearerToken).getExpiration();
		if (userHeader.equals(userSubject) && !expHeader.before(new Date())) {
			
			return true;

		}

		return false;
	}

}
