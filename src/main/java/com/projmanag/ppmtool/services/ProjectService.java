package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projmanag.ppmtool.domain.Backlog;
import com.projmanag.ppmtool.domain.Project;
import com.projmanag.ppmtool.exceptions.ProjectIdException;
import com.projmanag.ppmtool.repositories.BacklogRepository;
import com.projmanag.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project proj) {		
		try {
			//set the ID to uppercase
			String  projectIdentifierUpper = proj.getProjectIdentifier().toUpperCase();
			proj.setProjectIdentifier(projectIdentifierUpper);
			
			//if project is New (not updated) - create a Backlog in the DB
			if(proj.getId()	== null)
			{
				Backlog backlog = new Backlog(); 
				proj.setBacklog(backlog); //set backlog to project
				backlog.setProject(proj); //set project to backlog
				backlog.setProjectIdentifier(projectIdentifierUpper); //set proj ifentifier to backlog
				
			} else { //if project is Update, update it setting it's own backlog
				proj.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifierUpper));
								
			}
			
			//save it or update it into the DB
			return projectRepository.save(proj);		
			
		} catch (Exception e) {
			throw new ProjectIdException("Poject ID '"+proj.getProjectIdentifier().toUpperCase()+"' already exists");
		}		
	}
	
	public Project findProjectByIdentifier (String projectID) {
		
		Project project = projectRepository.findByProjectIdentifier(projectID.toUpperCase());
		
		if(project ==null) {
			throw new ProjectIdException("Poject ID '"+projectID+"' doesn't exists");
		}
		
		return project;
	}
	
	public Iterable<Project> findAllProject() {
	
		return projectRepository.findAll();
		
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		
		//prima di eliminarlo lo cerco nel DB
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());	
		//se non lo trovo notifico il Client
		if(project ==null) {
			throw new ProjectIdException("Cannot DELETE, Poject ID '"+projectId+"' doesn't exists");
		}	
		//cancello
		projectRepository.delete(project);			
		
	}
	

}
