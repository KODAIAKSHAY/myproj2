package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

public interface JobDAO {

	public List<Job> getAllOpenedJobs();

	public Job getJobDetails(int id);

	public boolean updateJob(Job job);

	public boolean updateJob(JobApplication jobApplication);

	public boolean save(JobApplication jobApplication);

	public boolean save(Job job);

	public List<Job> getMyAppliedJobs(String userID);
	
	 public List<JobApplication> listJobApplication();

	public JobApplication getJobApplication(int id);

	public JobApplication getJobApplication(String userID, int jobID);

	public boolean updateJobApplication(JobApplication jobApplication);
}
