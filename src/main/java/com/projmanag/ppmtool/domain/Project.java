package com.projmanag.ppmtool.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@NotBlank(message = "Project name is required")
	private String projectName;
	@NotBlank(message = "Project identifier is required")
	@Size(min = 4, max=5, message = "Please use 4to 5 characters")
	@Column(updatable = false, unique = true)
	private String projectIdentifier;
	@NotBlank(message = "Project description is required")
	private String description;
	@JsonFormat(pattern = "yyy-mm-dd")
	private Date start_date;
	@JsonFormat(pattern = "yyy-mm-dd")
	private Date end_date;
	
	@JsonFormat(pattern = "yyy-mm-dd HH:mm:ss")
	private Date created_At;
	@JsonFormat(pattern = "yyy-mm-dd HH:mm:ss")
	private Date updated_At;
	
	public Project() {
	}

	@PrePersist
	protected void onCreate(){
		this.created_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_At = new Date();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

	public Date getUpdated_At() {
		return updated_At;
	}

	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}
	
	
	
	
}