package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.projmanag.ppmtool.domain.Backlog;
import com.projmanag.ppmtool.domain.Project;
import com.projmanag.ppmtool.domain.ProjectTask;
import com.projmanag.ppmtool.exceptions.ProjectNotFoundException;
import com.projmanag.ppmtool.repositories.BacklogRepository;
import com.projmanag.ppmtool.repositories.ProjectRepository;
import com.projmanag.ppmtool.repositories.ProjectTaskRepository;
import com.sun.xml.bind.v2.TODO;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	/**
	 * Add a Project Task to DB wih th PT pased and the Project Identifier
	 * @param projectIdentifier
	 * @param projectTask
	 * @return
	 */
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		try {
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
			
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project not found!");
		}
		
		
	}
	
	public Iterable<ProjectTask> findBacklogById(String projectIdentifier) 	{
		
		Project proj = projectRepository.findByProjectIdentifier(projectIdentifier);
		
		if(proj==null)
		{
			throw new ProjectNotFoundException("Project with ID = '"+projectIdentifier+"' doesn't exist");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);	
	}
	
	public ProjectTask findPTByProjectSquence(String backlog_id, String pt_id) {
		
		//make sure to search on the correct/existing Backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog==null) {
			throw new ProjectNotFoundException("Project/Backlog with ID = '"+backlog_id+"' doesn't exist");
		}
		
		//make sure that out Task exists
		ProjectTask projTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projTask==null) {
			throw new ProjectNotFoundException("Project/ProjectTask with ID = '"+backlog_id+"' doesn't exist"); 
		}
		
		//make sure that the backlog/project ID in the path is the one that contains the searched Project Task
		if(!projTask.getProjectIdentifier().equals(backlog.getProjectIdentifier())){
			throw new ProjectNotFoundException("ProjectTask with ID = '"+pt_id+"' doesn't belong to Project with ID '"+backlog_id+"'"); 			
		}
		return projectTaskRepository.findByProjectSequence(pt_id);
	}
	
}
