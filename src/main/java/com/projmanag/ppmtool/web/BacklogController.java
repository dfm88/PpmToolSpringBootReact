package com.projmanag.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projmanag.ppmtool.domain.ProjectTask;
import com.projmanag.ppmtool.services.MapValidationErrorService;
import com.projmanag.ppmtool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id) {
		
		//check if there are errors and only in that case return the errors
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);		
		if(errorMap!= null ) return errorMap;
		//save the Project Task into the DB (methods returns the relative Project Task Object)
		ProjectTask projectTask2 = projectTaskService.addProjectTask(backlog_id, projectTask);	
		
		return new ResponseEntity<ProjectTask>( projectTask2, HttpStatus.CREATED);		
	}

}
