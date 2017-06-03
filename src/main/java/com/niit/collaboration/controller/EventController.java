package com.niit.collaboration.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.niit.collaboration.dao.EventDAO;
import com.niit.collaboration.model.Event;

@RestController
public class EventController {
	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	Event event;
	
	@Autowired
	EventDAO eventDAO;
	
	@RequestMapping(value = "/event/", method = RequestMethod.POST)
	public ResponseEntity<Event> createEvent(@RequestBody Event event) {
		Logger.debug("Start of method to create event");
		event.setDateTime(new Date());

		if (eventDAO.save(event) == true) {
			event.setErrorCode("200");
			event.setErrorMessage("Event successfully posted");
			Logger.debug("Event posting Successful");
			
		} else {
			event.setErrorCode("404");
			event.setErrorMessage("Unable to post a event");
			Logger.debug("not saved the event. post again...");
		}
		Logger.debug("End of method to create event");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
	
	@RequestMapping(value="/events" , method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getEvents()
	{
		Logger.debug("Start of method to list the events");
		List<Event> events = eventDAO.list();
		if(events==null){
			event=new Event();
			event.setErrorCode("404");
			event.setErrorMessage("No events are available");
			return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			Logger.debug("End of method to list the events");
			return new ResponseEntity<List<Event>>(events,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/event/{id}" , method=RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@PathVariable("id")int id){
		Logger.debug("Start of method to get a certain event with id"+id);
		Event event = eventDAO.get(id);
		if(event==null)
		{
			event = new Event();
			event.setErrorCode("404");
			event.setErrorMessage("event not found with id:"+id);
		}
		Logger.debug("End of method to get a certain event with id"+id);
			return new ResponseEntity<Event> (event,HttpStatus.OK);
	}
	
	@RequestMapping(value="/event/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Event> deleteEvent(@PathVariable int id)
	{
		Logger.debug("Start of method to delete an event with id:"+id);
		if(eventDAO.get(id)==null){
			return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
		}
		eventDAO.delete(id);
		Logger.debug("End of method to delete an event with id:"+id);
		return new ResponseEntity<Event>(HttpStatus.OK);
		}
}
