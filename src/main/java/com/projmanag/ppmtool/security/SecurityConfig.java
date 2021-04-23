package com.projmanag.ppmtool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf().disable() //disabled csrf because we're goining to use JWT
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) //to custumize Exception thrown we unrecognized
			.and() 
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //stateless sessione cause we're using JWT
			.and()
			.headers().frameOptions().sameOrigin() //to enable H2 DB for those who wants to run the App without MySql
			.and()
			.authorizeRequests() //path autorizzati anche senza login
			.antMatchers(
					 "/",
                     "/favicon.ico",
                     "/**/*.png",
                     "/**/*.gif",
                     "/**/*.svg",
                     "/**/*.jpg",
                     "/**/*.html",
                     "/**/*.css",
                     "/**/*.js"
             ).permitAll()
			.antMatchers("/api/users/**").permitAll() //momentaneo giusto per provare ad INSERT user
			.anyRequest().authenticated();
			 
	}
	
	

}
