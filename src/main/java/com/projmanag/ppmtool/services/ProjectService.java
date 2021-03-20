package com.projmanag.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projmanag.ppmtool.domain.Project;
import com.projmanag.ppmtool.exceptions.ProjectIdException;
import com.projmanag.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project proj) {
		
		try {
			proj.setProjectIdentifier(proj.getProjectIdentifier().toUpperCase());
			return projectRepository.save(proj);		
			
		} catch (Exception e) {
			throw new ProjectIdException("Poject ID '"+proj.getProjectIdentifier().toUpperCase()+"' already exists");
		}
		
	}

}
