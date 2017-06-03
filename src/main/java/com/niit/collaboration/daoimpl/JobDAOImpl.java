package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

@Repository("jobDAO")
public class JobDAOImpl implements JobDAO{
	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(JobDAOImpl.class);
	private static final Job Job = null;
	@Autowired
	private SessionFactory sessionFactory;
	public JobDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}
	@Transactional
	public List<Job> getAllOpenedJobs() {
		String hql = "from Job where status = '"+"V'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public Job getJobDetails(int id) {
	    Logger.debug("Start of method to getJobDetails");
		return (com.niit.collaboration.model.Job) sessionFactory.getCurrentSession().get(Job.class,id);
	}

	@Transactional
	public boolean updateJob(Job job) {
		try{
			sessionFactory.getCurrentSession().update(job);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean updateJob(JobApplication jobApplication) {
		try{
			sessionFactory.getCurrentSession().update(jobApplication);
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean save(JobApplication jobApplication) {
		sessionFactory.getCurrentSession().save(jobApplication);
		return false;
	}
	
	@Transactional
	public boolean save(Job job) {
		Logger.debug("Start of method to save job");
		try {
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (HibernateException e) {
			Logger.debug("End of method to save job");
			e.printStackTrace();
			return false;
		}
		
	}
	@Transactional
	public List<Job> getMyAppliedJobs(String userID) {
		Logger.debug("Start of method to getMyAppliedJobs"+userID);
		String hql = "from JobApplication where userID = '" + userID + "'";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		Logger.debug("End of method to getMyAppliedJobs");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<JobApplication> listJobApplication(){
		
		String hql = "from JobApplication";
	@SuppressWarnings("rawtypes")
	Query query =sessionFactory.getCurrentSession().createQuery(hql);
	
	List<JobApplication> listJobApplication = query.list();
	if(listJobApplication == null  || listJobApplication.isEmpty())
	{
		 return null;
		 
	}
	return query.list();
	}
	
	@Transactional
	public JobApplication getJobApplication(int id) {
		Logger.debug("start of method getJobApplication");
		String hql="from JobApplication where id = " + "'" + id + "'";
		
		@SuppressWarnings({ "rawtypes" })
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings({ "unchecked" })
		List<JobApplication> list=query.list();
		if(list==null || list.isEmpty())
		{
			
			return null;
		}
		else
		{
			return list.get(0);
		}	
	}

	@Transactional
	public JobApplication getJobApplication(String userID, int jobID) {
		String hql = "from JobApplication where userID='"+"userID' and JobID="+jobID;
		return (JobApplication) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}

	@Transactional
	public boolean updateJobApplication(JobApplication jobApplication) {
		try {
			sessionFactory.getCurrentSession().update(jobApplication);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
