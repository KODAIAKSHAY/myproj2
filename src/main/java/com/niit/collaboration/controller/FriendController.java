package com.niit.collaboration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.dao.UserDetailsDAO;
import com.niit.collaboration.model.Friend;

@RestController
public class FriendController {
	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(FriendController.class);
	
	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	Friend friend;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserDetailsDAO userDAO;
	
	
	@RequestMapping(value="/myFriends", method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriends(){
		Logger.debug("Calling method getMyFriends()");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		List<Friend> myFriends = new ArrayList<Friend>();
		if(loggedInUserID==null){
			friend.setErrorCode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends,HttpStatus.OK);
		}
		Logger.debug("getting friends of:"+loggedInUserID);
		myFriends = friendDAO.getMyFriend(loggedInUserID);
		
		if(myFriends.isEmpty()){
			Logger.debug("Friends do not exist for the user:"+loggedInUserID);
			friend.setErrorCode("404");
			friend.setErrorMessage("You do not have any friends");
			myFriends.add(friend);
		}
		Logger.debug("Send the friends list");
		return new ResponseEntity<List<Friend>>(myFriends,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getMyFriendRequests/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequests(){
		Logger.debug("Calling method getMyFriendRequests()");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		System.out.println(loggedInUserID);
		List<Friend> myFriendRequests = friendDAO.getMyFriendRequests(loggedInUserID);
		System.out.println(myFriendRequests);
		return new ResponseEntity<List<Friend>>(myFriendRequests,HttpStatus.OK);
	}
		 
	private boolean isUserExist(String id){
		if(userDAO.getUser(id)==null)
			return false;
		else
			return true;
	}
	
	@RequestMapping(value = "/addFriend/{friendID}",method=RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendID")String friendID){
		Logger.debug("calling method sendFriendRequest");
		String loggedInUSerID = (String) session.getAttribute("loggedInUserID");
		friend.setUserID(loggedInUSerID);
		friend.setFriendID(friendID);
		friend.setStatus("N");
		friend.setIsOnline('N');
		
		if(isUserExist(friendID)==false){
			friend.setErrorCode("404");
			friend.setErrorMessage("No user exist with id:"+friendID);	
		}
		
		if(friendDAO.get(loggedInUSerID, friendID)!=null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("You already sent a request");
			
		}
		else{
			friendDAO.save(friend);
			friend.setErrorCode("200");
			friend.setErrorMessage("Friend request successful.."+friendID);
			
		}
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	/*@RequestMapping(value="/acceptFriend/{friendID}",method=RequestMethod.GET)
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendID")String friendID){
		Logger.debug("calling method acceptFriendRequest");
		friend = updateRequest(friendID,"A");
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}*/
	
	private Friend updateRequest(String friendID, String status){
		Logger.debug("Start of method updateRequest");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		Logger.debug("loggedInUserID:"+loggedInUserID);
		
		if(isFriendRequestAvailable(friendID)==false){
			friend.setErrorCode("404");
			friend.setErrorMessage("The request does not exist. so you cannot update to "+status);
		}
		
		if(status.equals("A")||status.equals("R"))
			friend = friendDAO.get(friendID, loggedInUserID);
		else
			friend = friendDAO.get(loggedInUserID, friendID);
		friend.setStatus(status);
		
		friendDAO.update(friend);
		
		friend.setErrorCode("200");
		friend.setErrorMessage("Request from "+friend.getUserID() + "To"+friend.getFriendID() +"has updated to : "+status);
		Logger.debug("End of method updateRequest");
		return friend;
	}
	
	 @RequestMapping(value="/friendaccept/{id}" , method = RequestMethod.PUT)
	   public  ResponseEntity<Friend> friendaccept(@PathVariable("id") int id, @RequestBody Friend friend ) 
	   {
		 Logger.debug("Start of method friendaccept:"+id);
		   friend = friendDAO.get(friend.getId());
		   System.out.println(friend);
		
		  if(friend==null)
		  {
			  Logger.debug("->->->->User does not exist with id "+ friend.getId());
			  friend = new Friend();
			  friend.setErrorMessage("User does not exist with id "+ friend.getId());
			   return new ResponseEntity<Friend>(friend, HttpStatus.NOT_FOUND);
		  }
		  
		  friend.setStatus("A");
		   
		  friendDAO.update(friend);
		   Logger.debug("End of method friendaccept");
		   return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	   }
	
	 @RequestMapping(value="/friendreject/{id}" , method = RequestMethod.PUT)
	   public  ResponseEntity<Friend> friendreject(@PathVariable("id") int id, @RequestBody Friend friend ) 
	   {
		 Logger.debug("Start of method friendreject:"+id);
		   friend = friendDAO.get(friend.getId());
		
		  if(friend==null)
		  {
			  Logger.debug("->->->->User does not exist with id "+ friend.getId());
			  friend = new Friend();
			  friend.setErrorMessage("User does not exist with id "+ friend.getId());
			   return new ResponseEntity<Friend>(friend, HttpStatus.NOT_FOUND);
		  }
		  
		  friend.setStatus("R");
		   
		  friendDAO.update(friend);
		  Logger.debug("end of method friendreject:"+id);
		   return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	   }
	
	
	
	private boolean isFriendRequestAvailable(String friendID){
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		if(friendDAO.get(loggedInUserID, friendID)==null)
			return false;
		else
			return true;
	}
	
	@RequestMapping(value="/unFriend/{friendID}",method = RequestMethod.GET)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendID")String friendID){
		Logger.debug("Calling method unFriend");
		updateRequest(friendID,"U");
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectFriend/{friendID}" , method=RequestMethod.GET)
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("friendID")String friendID){
		Logger.debug("Calling method rejectFriendRequest");
		updateRequest(friendID,"R");
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getRequestsSentByMe/")
	public ResponseEntity<List<Friend>> getRequestsSentByMe(){
		Logger.debug("Calling method getRequestsSentByMe");
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		List<Friend> requestsSentbyMe = friendDAO.getRequestsSentByMe(loggedInUserID);
		Logger.debug("Ending method getRequestsSentByMe");
		return new ResponseEntity<List<Friend>>(requestsSentbyMe,HttpStatus.OK);
	}
}
