package com.niit.collaboration.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="c_chat_forum")
@Component
public class ChatForum extends BaseDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String name;	
private String userID;
private String message;
private Date createdDate;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getUserID() {
	return userID;
}
public void setUserID(String userID) {
	this.userID = userID;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}

}
