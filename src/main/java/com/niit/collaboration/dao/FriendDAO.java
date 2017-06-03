package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Friend;

public interface FriendDAO {
	
	public List<Friend> getMyFriend(String userID);
	
	public List<Friend> getRequestsSentByMe(String userID);

	public Friend get(String userID, String friendID);

	public Friend get(int id);

	public boolean save(Friend friend);

	public boolean update(Friend friend);

	public boolean delete(String userID, String friendID);

	public List<Friend> getMyFriendRequests(String userID);

	public void setOnline(String loggedInUserID);

	public void setOffLine(String loggedInUserID);
}
