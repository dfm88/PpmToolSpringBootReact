package com.projmanag.ppmtool.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projmanag.ppmtool.domain.Project;
import com.projmanag.ppmtool.services.MapValidationErrorService;
import com.projmanag.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")

public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired MapValidationErrorService mapValidatinErrorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
		
		ResponseEntity<?> errorMap = mapValidatinErrorService.MapValidationService(result);		
		if(errorMap!= null ) return errorMap;	
		
		Project proj1 = projectService.saveOrUpdateProject(project);	
		
		return new  ResponseEntity<Project>(proj1, HttpStatus.CREATED);
	}
	
	

}
