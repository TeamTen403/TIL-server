package com.teamten.til.common.config.auth.token;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.teamten.til.common.config.AppProperties;
import com.teamten.til.tiler.entity.Tiler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class TokenProvider {

	private final AppProperties appProperties;
	@Getter
	@Setter
	private String jwtMsg;

	public String createToken(Tiler tiler) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		return Jwts.builder()
			.setSubject(tiler.getId().toString())
			.setIssuedAt(new Date())
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
			.compact();
	}

	public UUID getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(appProperties.getAuth().getTokenSecret())
			.parseClaimsJws(token)
			.getBody();

		return UUID.fromString(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
		return true;
	}

}
