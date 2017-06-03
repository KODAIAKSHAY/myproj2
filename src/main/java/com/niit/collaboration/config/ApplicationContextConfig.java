package com.niit.collaboration.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.Chat;
import com.niit.collaboration.model.ChatForum;
import com.niit.collaboration.model.Event;
import com.niit.collaboration.model.Friend;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;
import com.niit.collaboration.model.UserDetails;

@Configuration
@Component
@ComponentScan("com.niit.collaboration")
@EnableTransactionManagement

public class ApplicationContextConfig {
	@Bean(name = "dataSource")
	public DataSource getOracleDataSource() 
	{
		DriverManagerDataSource datasource=new DriverManagerDataSource();
		datasource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		datasource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");	
		datasource.setUsername("system");
	    datasource.setPassword("system");
	    Properties connectionProperties = new Properties();
	    connectionProperties.setProperty("hibernate.hbm2ddl.auto", "update");
	    connectionProperties.setProperty("hibernate.show_sql", "true");
	    connectionProperties.setProperty("hibernte.dialect", "org.hibernate.dialect.Oracle10gDialect");
	    connectionProperties.setProperty("hiberanate.formate_sql", "true");
	    connectionProperties.setProperty("hibernate.jdbc.use_get_generated_keys", "true");
	    		
	    datasource.setConnectionProperties(connectionProperties);
	    
	    return datasource;
		
	}

	private Properties getHibernateProperties() 
	{
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");
		return properties;
	}

	@Autowired
	@Bean(name = "sessionfactory")
	public SessionFactory getSessionFactory(DataSource datasouce) 
	{
		 LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(getOracleDataSource());
		 sessionBuilder.addProperties(getHibernateProperties());
		 sessionBuilder.addAnnotatedClass(UserDetails.class);
		 sessionBuilder.addAnnotatedClass(Blog.class);
		sessionBuilder.addAnnotatedClass(Job.class);
		sessionBuilder.addAnnotatedClass(Event.class);
		sessionBuilder.addAnnotatedClass(Friend.class);
		sessionBuilder.addAnnotatedClass(JobApplication.class);
		sessionBuilder.addAnnotatedClass(ChatForum.class);
		sessionBuilder.addAnnotatedClass(Chat.class);
		return sessionBuilder.buildSessionFactory();

	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionfactory) {

		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionfactory);
		return transactionManager;

	}
}
