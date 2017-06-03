package com.niit.collaboration.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

@RestController
public class JobController {
	private static final Logger log = LoggerFactory.getLogger(JobController.class);

	@Autowired
	Job job;

	@Autowired
	JobApplication jobApplication;

	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	HttpSession httpsession;

	@RequestMapping(value = "/getAlljobs", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAllOpenedJobs() {
		log.debug("Start of method to getAllOpenedJobs ");
		List<Job> jobs = jobDAO.getAllOpenedJobs();
		log.debug("End of method to getAllOpenedJobs");
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);

	}

	@RequestMapping(value = "/getMyAppliedJobs/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getMyAppliedJobs(HttpSession httpsession) {
		log.debug("Start of method to getMyAppliedJobs");
		String loggedInUserID = (String) httpsession.getAttribute("loggedInUserID");
		List<Job> jobs = new ArrayList<Job>();
		if(loggedInUserID==null||loggedInUserID.isEmpty()){
			job.setErrorCode("404");
			job.setErrorMessage("Please login to continue....");
			jobs.add(job);
		}
		else
		{
		jobs = jobDAO.getMyAppliedJobs(loggedInUserID);
		}
		log.debug("End of method to getMyAppliedJobs");
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/getAllJobsApplication/" , method = RequestMethod.GET)
	public ResponseEntity<List<JobApplication>> getjobsapplied()
	{
		List<JobApplication> jobapplied = jobDAO.listJobApplication();
		if(jobapplied == null)
		{
			job = new Job();
			 job.setErrorCode("404");
       	  job.setErrorMessage("No jobapplied are available");
       	  return new ResponseEntity<List<JobApplication>>(HttpStatus.NO_CONTENT);
		}
		 else
         {
       	  return new ResponseEntity<List<JobApplication>>(jobapplied, HttpStatus.OK);
         }
	}

	@RequestMapping(value = "/getjobDetails/{jobID}", method = RequestMethod.GET)
	public ResponseEntity<Job> getJobDetails(@PathVariable("jobID") int jobID) {
		log.debug("Start of method to getJobDetails");
		Job job = jobDAO.getJobDetails(jobID);
		Date dateTime = job.getDateTime();
		SimpleDateFormat format = new SimpleDateFormat("DD/MM/YYYY");
		
		if (job==null){
			job=new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Job not available for this id:"+jobID);
			
		}
		log.debug("End of method to getJobDetails");
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/postAJob/", method = RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job) {
		log.debug("Start of method to postAJob");
		job.setStatus('V');
		job.setDateTime(new Date());

		if (jobDAO.save(job) == true) {
			job.setErrorCode("200");
			job.setErrorMessage("Job successfully posted");
			log.debug("Job posting Successful");
			
		} else {
			job.setErrorCode("404");
			job.setErrorMessage("Unable to post a job");
			log.debug("not saved the job. post again...");
		}
		log.debug("End of method to postAJob");
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/applyForJob/{jobID}", method = RequestMethod.POST)
	public ResponseEntity<Job> applyForJob(@PathVariable("jobID") int jobID, HttpSession httpsession) {
		log.debug("Start of method to applyForJob");
		String loggedInUserID = (String) httpsession.getAttribute("loggedInUserID");
		
		if (loggedInUserID == null || loggedInUserID.isEmpty()) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("please job to apply for the job...");
		}
		else{
		jobApplication.setJobID(jobID);
		jobApplication.setUserID(loggedInUserID);
		jobApplication.setStatus('N');
		jobApplication.setDateTime(new Date(System.currentTimeMillis()));
		log.debug("AppliedDate:" + jobApplication.getDateTime());
		
		if (jobDAO.getJobApplication(loggedInUserID, jobID)==null)
		{
			if(jobDAO.save(jobApplication)){
			job.setErrorCode("200");
			job.setErrorMessage("Successfully applied for job");
			log.debug("Successfully applied for job");
			}
		}
		else{
			job.setErrorCode("400");
			job.setErrorMessage("Unable to apply for job..try again later");
			log.debug("Unable to apply for job..try again later");
		}
		}
		log.debug("End of method to applyForJob");
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/selectUser/{userID}/{jobID}", method = RequestMethod.PUT)
	public ResponseEntity<Job> selectUser(@PathVariable("userID") String userID, @PathVariable("jobID") int jobID,
			String remarks) {
		log.debug("Start of method to selectUser");
		jobApplication = updateJobApplicationStatus(userID, jobID, 'S', remarks);
		log.debug("End of method to selectUser");
		return new ResponseEntity<Job>(job, HttpStatus.OK);

	}

	@RequestMapping(value = "/callForInterview/{userID}/{jobID}", method = RequestMethod.PUT)
	public ResponseEntity<Job> callForInterview(@PathVariable("userID") String userID, @PathVariable("jobID") int jobID,
			String remarks) {
		log.debug("Start of method to callForInterview");
		jobApplication = updateJobApplicationStatus(userID, jobID, 'C', remarks);
		log.debug("End of method to callForInterview");
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectJobApplication/{userID}/{jobID}", method = RequestMethod.PUT)
	public ResponseEntity<Job> rejectJobApplication(@PathVariable("userID") String userID,
			@PathVariable("jobID") int jobID, String remarks) {
		log.debug("Start of method to rejectJobApplication");
		jobApplication = updateJobApplicationStatus(userID, jobID, 'R', remarks);
		log.debug("End of method to rejectJobApplication");
		return new ResponseEntity<Job>(job, HttpStatus.OK);

	}

	private JobApplication updateJobApplicationStatus(String userID, int jobID, char status, String remarks) {
		log.debug("start of method updateJobApplicationStatus");
		
		if(isUserAppliedForTheJob(userID,jobID)==false){
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage(userID+"not applied for the job"+jobID);
			return jobApplication;
		}
		
		String loggedInUserRole = (String) httpsession.getAttribute("loggedInUserRole");
		log.debug("loggedInUserRole:"+loggedInUserRole);
		if(loggedInUserRole==null||loggedInUserRole.isEmpty()){
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("You are not logged in");
			return jobApplication;
		}
		
		if(!loggedInUserRole.equalsIgnoreCase("admin")){
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("You are not admin");
			return jobApplication;
		}
		
		String loggenInUserID  = (String) httpsession.getAttribute("loggedInUserID");
		jobApplication = jobDAO.getJobApplication(loggenInUserID, jobID);
		jobApplication.setStatus(status);
		jobApplication.setRemarks(remarks);
		if (jobDAO.updateJob(jobApplication)) {
			jobApplication.setErrorCode("200");
			jobApplication.setErrorMessage("Successfully updated status to"+status);
			log.debug("Successfully updated status to"+status);
		}
		else{
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage(" not success to update:"+status);
			log.debug(" not success to update:"+status);
		}
		log.debug("end of method updateJobApplicationStatus");
		return jobApplication;
	} 
	
	private boolean isUserAppliedForTheJob(String userID,int jobID){
		if(jobDAO.getJobApplication(userID, jobID)==null){
			return false;
		}
		return true;
	}
	
	@RequestMapping(value="/jobaccept/{id}" , method = RequestMethod.PUT)
	   public  ResponseEntity<JobApplication> jobaccept(@PathVariable("id") int id, @RequestBody JobApplication jobApplication ) 
	   {
		log.debug("Start of method jobaccept");
		 jobApplication = jobDAO.getJobApplication(jobApplication.getId()); 
		 System.out.println(jobApplication);
		 
		  if(jobApplication==null)
		  {
			 
			  jobApplication = new JobApplication();
			  jobApplication.setErrorMessage("User does not exist with id "+ jobApplication.getId());
			   return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		  }
		  
		  jobApplication.setStatus('A');
		  jobApplication.setRemarks("Approved");
		  jobDAO.updateJobApplication(jobApplication); 
		  
		   
		   return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	   }
	
	
	@RequestMapping(value="/jobreject/{id}" , method = RequestMethod.PUT)
	   public  ResponseEntity<JobApplication> jobreject(@PathVariable("id") int id, @RequestBody JobApplication jobApplication ) 
	   {
		 jobApplication = jobDAO.getJobApplication(jobApplication.getId());  
		 
		  if(jobApplication==null)
		  {
			 
			  jobApplication = new JobApplication();
			  jobApplication.setErrorMessage("User does not exist with id "+ jobApplication.getId());
			   return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NOT_FOUND);
		  }
		  
		  jobApplication.setStatus('R');
		  jobApplication.setRemarks("Not approved");
		  jobDAO.updateJobApplication(jobApplication); 
		  
		   
		   return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	   }
}
