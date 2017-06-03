package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.api.log.Log;
import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.model.Friend;

@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO {
	
	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	private static final Friend Friend = null;
	
	@Autowired
	private SessionFactory sessionFactory;

	private Friend friend;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		try{
		this.sessionFactory = sessionFactory;
		}
		catch(Exception e){
			Logger.error("Unable to connet to database");
			e.printStackTrace();
		}
	}
 

	public FriendDAOImpl() {

	}

	@Transactional
	public List<com.niit.collaboration.model.Friend> getMyFriend(String userID) {
		Logger.debug("Start of method getMyFriend of user"+userID);
		String hql1 = "from Friend where userID='"+userID+"' and status='"+"A'";
		            
		 String hql2= "from Friend where friendID='"+userID+"' and status='"+"A'";
		             
		Logger.debug("getMyFriends hql1:"+hql1);
		Logger.debug("getMyFriends hql2:"+hql2);
		Query query1 = sessionFactory.openSession().createQuery(hql1);
		Query query2 = sessionFactory.openSession().createQuery(hql2);
		List<Friend> list1 =(List<Friend>)query1.list();
		List<Friend> list2 =(List<Friend>)query2.list();
		
		list1.addAll(list2);
		System.out.println(list1);;
		Logger.debug("End of method getMyFriend of user"+userID);
		return list1;
	}

	@Transactional
	public com.niit.collaboration.model.Friend get(String userID, String friendID) {
		String hql = "from Friend where userID=" + "'" + userID + "' and friendID = '" + friendID +"'";
		Logger.debug("hql:"+hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		return (com.niit.collaboration.model.Friend) query.uniqueResult();
		
	}

	@Transactional
	public com.niit.collaboration.model.Friend get(int id) {
		Logger.debug("Start of method to get friend id:"+id);
		Session session=sessionFactory.getCurrentSession();
		System.out.println("opened the session");
		Criteria ct=session.createCriteria(Friend.class);
		System.out.println("opened the criteria");
		ct.add(Restrictions.eq("id",id));
		System.out.println(id);
		Friend f = (com.niit.collaboration.model.Friend) ct.uniqueResult();
		Logger.debug("End of method to get friend id");
		return f;	
	}

	@Transactional
	public boolean save(com.niit.collaboration.model.Friend friend) {
		try{
			Logger.debug("Start of method to save friend");
			  sessionFactory.getCurrentSession().save(friend);
		return true;
			}catch (Exception e ){
				e.printStackTrace();
				Logger.debug("End of method to save friend");
				return false;
			}
	}

	@Transactional
	public boolean update(com.niit.collaboration.model.Friend friend) {
		try{
			Logger.debug("Start of method to update friend");
			sessionFactory.getCurrentSession().update(friend);
	return true;
		} catch (Exception e){
	       e.printStackTrace();
	       Logger.debug("End of method to update friend");
	       return false;
		}
	}

	@Transactional
	public boolean delete(String userID, String friendID) {
		Friend friend = new Friend();
		friend.setFriendID(friendID);
		friend.setUserID(userID);
		sessionFactory.getCurrentSession().delete(friend);
		return true;
	}

	@Transactional
	public List<com.niit.collaboration.model.Friend> getMyFriendRequests(String userID) {
		Logger.debug("Start of method getNewFriendRequests");
		Logger.debug(userID);
		String hql = "from Friend where friendID=" + "'" + userID + "' and status = '" + "N'";
		
		Logger.debug(hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Logger.debug("hiiiiiiiiii");
		List<Friend> list = (List<Friend>) query.list();
		System.out.println(list);
		return list;
	}

	@Transactional
	public void setOnline(String loggedInUserID) {
		Logger.debug("Starting of the method setOnline"); 
		String hql = "UPDATE Friend SET isOnline = 'Y' where userID ='" + loggedInUserID + "'";
		Logger.debug("hql: " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		Logger.debug("Ending of the method setOnline");
		
	}

	@Transactional
	public void setOffLine(String loggedInUserID) {
		Logger.debug("Starting of the method setOffline");
		String hql = "UPDATE Friend SET isOnline = 'N' where userID = '" + loggedInUserID + "'";
		Logger.debug("hql: " + hql);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		Logger.debug("Ending of the method setOffline");
		
	}
	


	@Transactional
	public List<com.niit.collaboration.model.Friend> getRequestsSentByMe(String userID) {
		String hql = "from Friend where userID=" + "'" + userID + "' and status='"+"N'";
		Logger.debug(hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		List<Friend> list = (List<Friend>) query.list();
		return list;
	}
}
