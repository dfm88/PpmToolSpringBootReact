package com.projmanag.ppmtool.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projmanag.ppmtool.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
    User getById(Long id);
    
    //alternativamente (meglio) usare Optional per tornare l'user
    //per evitare NullPointerExceptions
    /* Optional<User> findById(Long id); */
	

}

