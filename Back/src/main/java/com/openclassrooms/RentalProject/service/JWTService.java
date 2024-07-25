package com.openclassrooms.RentalProject.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * Service handling with the JSON Web Tokens.
 */
@Service
public class JWTService {

	private JwtEncoder jwtEncoder;

	public JWTService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	/**
	 * Generates a token with RS256 algorithm which last 1 day.
	 * 
	 * @param userDetails its user name is used to generate the token. 
	 * @return the token.
	 */
	public String generateToken(UserDetails userDetails) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.DAYS)).subject(userDetails.getUsername()).build();

		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
				.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claims);

		return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
	}

}