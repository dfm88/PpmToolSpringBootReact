package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projmanag.ppmtool.domain.Backlog;
import com.projmanag.ppmtool.domain.ProjectTask;
import com.projmanag.ppmtool.repositories.BacklogRepository;
import com.projmanag.ppmtool.repositories.ProjectTaskRepository;
import com.sun.xml.bind.v2.TODO;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	/**
	 * Add a Project Task to DB wih th PT pased and the Project Identifier
	 * @param projectIdentifier
	 * @param projectTask
	 * @return
	 */
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		//TODO Exceptions Handling
		//Get the backlog related to the new Task, from the DB
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		projectTask.setBacklog(backlog);
		//Get the last Sequence No from back log and assign it to PT incremented by 1
		Integer newSequenceTaskNum =  backlog.getPTSequence()+1;
		backlog.setPTSequence(newSequenceTaskNum);
		projectTask.setProjectSequence(projectIdentifier+"-"+newSequenceTaskNum);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		//set initial priority if not specified
		if(projectTask.getPriority()==null){
			projectTask.setPriority(3);
		}
		//set initial status if not specified
		if(projectTask.getStatus()==null||projectTask.getStatus().isBlank() ){
			projectTask.setStatus("TO_DO");
		}
		
		return projectTaskRepository.save(projectTask);
		
	}
}
