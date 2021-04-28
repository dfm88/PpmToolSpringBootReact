package com.projmanag.ppmtool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.projmanag.ppmtool.services.CustomUserDetailsService;

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
	
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();   }



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
			.antMatchers(SecurityConstants.SIGN_UP_URLS).permitAll() //momentaneo giusto per provare ad INSERT user
			.antMatchers(SecurityConstants.H2_URL).permitAll() //NEL CASO IN CUI VOLESSIMO USARE H2 DB
			.anyRequest().authenticated();
			 
	}
	
	

}
