package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projmanag.ppmtool.domain.User;
import com.projmanag.ppmtool.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	//prelevo user dalla repository
        User user = userRepository.findByUsername(username);
        //se è null lancio l'eccezione
        if(user==null) throw new UsernameNotFoundException("User not found");
        //altrimenti ritorno l'user
        return user;
    }

    //@TRANSACTIONAL serve per effettuare il rollback in caso di errori
    //ma in questo caso la si usa solo per controllare che l'id dell'user esista nel DB
    //lanciare eventuale eccezione e in caso positivo procedere con la verifica del JWT
    @Transactional
    public User loadUserById(Long id){
        User user = userRepository.getById(id);
        if(user==null) throw new UsernameNotFoundException("User not found");
        return user;
    }
}