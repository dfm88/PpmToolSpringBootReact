package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projmanag.ppmtool.domain.User;
import com.projmanag.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired //Bean in main file PpmtoolApplication.java
	private BCryptPasswordEncoder bCryptPasswordEncoder;

 	public User saveUser (User newUser){
	  //encrypt user password
      newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

      //Username has to be unique (exception)

        // Make sure that password and confirmPassword match
        // We don't persist or show the confirmPassword
      return userRepository.save(newUser);
    }

}
