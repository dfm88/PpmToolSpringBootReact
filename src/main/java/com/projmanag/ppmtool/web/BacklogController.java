package com.projmanag.ppmtool.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {
		return projectTaskService.findBacklogById(backlog_id);
		
	}
	
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
		
		ProjectTask projTask = projectTaskService.findPTByProjectSquence(backlog_id, pt_id);	
		
		return new ResponseEntity<ProjectTask> (projTask, HttpStatus.OK	);
	}
	
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projTask,  BindingResult result,
											   @PathVariable String backlog_id, @PathVariable String pt_id){
		
		//check if there are errors and only in that case return the errors
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);		
		if(errorMap!= null ) return errorMap;
		//save the Project Task into the DB (methods returns the relative Project Task Object)
		ProjectTask updatedProjectTask = projectTaskService.updateByProjectSequence(projTask, backlog_id, pt_id);	
		
		return new ResponseEntity<ProjectTask>( updatedProjectTask, HttpStatus.OK);
	}
	
	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){

		projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);	
		
		return new ResponseEntity<String>( "Project Task "+pt_id+" wad Deleted succesfully", HttpStatus.OK);
	} 

	
	
}
