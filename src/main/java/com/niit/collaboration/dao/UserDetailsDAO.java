package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.UserDetails;

public interface UserDetailsDAO {
	public boolean saveUser(UserDetails user);
	public boolean updateUser(UserDetails user);
	public boolean removeUser(int id);
	public boolean delete(UserDetails user);
	public UserDetails getUser(String id);
	public List<UserDetails> list();
	public UserDetails isValidUser(String id, String password);
	public void setOffLine(String loggedInUserID);
    public void setOnline(String loggedInUserID);
    public boolean saveOrupdate(UserDetails user);
}
