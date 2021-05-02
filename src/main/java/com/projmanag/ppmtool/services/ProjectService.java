package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projmanag.ppmtool.domain.Backlog;
import com.projmanag.ppmtool.domain.Project;
import com.projmanag.ppmtool.domain.User;
import com.projmanag.ppmtool.exceptions.ProjectIdException;
import com.projmanag.ppmtool.exceptions.ProjectNotFoundException;
import com.projmanag.ppmtool.repositories.BacklogRepository;
import com.projmanag.ppmtool.repositories.ProjectRepository;
import com.projmanag.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdateProject(Project proj, String principalUserName) {
		
		//verificare l'update del solo proprio progetto
		//e la non possibilita di creare un progetto passando un id qualsiasi dall'update
		if(proj.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(proj.getProjectIdentifier());
            if(existingProject !=null &&(!existingProject.getProjectLeader().equals(principalUserName))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+proj.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }
		 
		try {
			//get the user who is creating the Project
			User user = userRepository.findByUsername(principalUserName);
			//set The User in the Project
			proj.setUser(user);
			proj.setProjectLeader(user.getUsername());			
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
	
	public Project findProjectByIdentifier (String projectID, String principalUserName) {
		
		Project project = projectRepository.findByProjectIdentifier(projectID.toUpperCase());
		//prima controllo che il Project esista
		if(project ==null) {
			throw new ProjectIdException("Poject ID '"+projectID+"' doesn't exists");
		}
		//se il project esiste devo controllare che il richiedente ne sia il proprietario
		if(!project.getProjectLeader().equals(principalUserName)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}
		
		return project;
	}
	
	public Iterable<Project> findAllProject(String principalUserName) {
	
		return projectRepository.findByProjectLeader(principalUserName);
		
	}
	
	public void deleteProjectByIdentifier(String projectId, String principalUserName) {	
		//cancello
		projectRepository.delete(findProjectByIdentifier(projectId, principalUserName));		
		
	}
	

}
