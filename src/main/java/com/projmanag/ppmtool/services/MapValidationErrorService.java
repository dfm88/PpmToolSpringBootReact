package com.projmanag.ppmtool.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {

	/**
	 * 
	 * @param result
	 * @return a Map with <String, String> = <Name of the Field - Name of the Error>
	 */
	public ResponseEntity<?> MapValidationService(BindingResult result) {
		
		//if the are errors
		if(result.hasErrors()) {

			Map<String, String> errorMap = new HashMap<>(); //create the Map

			//for every error let's populate the Map with
			// <the Field , the Error message>
			for(FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}

			//in case of errors return a Response Entity of type Map<String, String>
			return new ResponseEntity<Map<String, String>> (errorMap, HttpStatus.BAD_REQUEST);
		}

		return null;
	}

}
