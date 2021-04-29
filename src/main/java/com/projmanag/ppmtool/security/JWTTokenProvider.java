package com.projmanag.ppmtool.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.projmanag.ppmtool.domain.User;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JWTTokenProvider {

    //Generate the token

	//metodo attivato quando l'User è stato autenticato e vogliamo 
	//generare il Token 
    public String generateToken(Authentication authentication){
    	//preleviamo il Principal dall'User già autenticato
        User user = (User)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+SecurityConstants.EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        //Claims contiene le informazioni dell'User,
        //può essere passata uno ad uno nella creazione del JWT
        //oppure creiamo prima una Map
        Map<String,Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        //Generatore di Token
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
        }

    	//Validate the token
	    public boolean validateToken(String token){
	        try{
	        	//valida il token ricevuto a seconda della Chiave Segreta impostata
	            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
	            return true;
	        }catch (SignatureException ex){
	            System.out.println("Invalid JWT Signature");
	        }catch (MalformedJwtException ex){
	            System.out.println("Invalid JWT Token");
	        }catch (ExpiredJwtException ex){
	            System.out.println("Expired JWT token");
	        }catch (UnsupportedJwtException ex){
	            System.out.println("Unsupported JWT token");
	        }catch (IllegalArgumentException ex){
	            System.out.println("JWT claims string is empty");
	        }
	        return false;
	    }


	    //Get user Id from token	
	    public Long getUserIdFromJWT(String token){
	    	//estrae il body dal Token prima, per poi estrarne l'id
	        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
	        String id = (String)claims.get("id");
	
	        return Long.parseLong(id);
	    }
}