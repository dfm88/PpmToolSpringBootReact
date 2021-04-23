package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projmanag.ppmtool.domain.User;
import com.projmanag.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.projmanag.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired //Bean in main file PpmtoolApplication.java
	private BCryptPasswordEncoder bCryptPasswordEncoder;

 	public User saveUser (User newUser){
      
      try {    	  
    	  //encrypt user password
    	  newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));	
    	  //Username has to be unique (exception) --line to make the Exception happens--
    	  newUser.setUsername(newUser.getUsername());
    	  // Make sure that password and confirmPassword match
          // We don't persist or show the confirmPassword
    	  newUser.setConfirmPassword(""); //JsongIgnore doesn't works because it applies befor validating password
    	  return userRepository.save(newUser);
      } catch (Exception e) {
    	  throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");		
      }

      

        
    }

}
