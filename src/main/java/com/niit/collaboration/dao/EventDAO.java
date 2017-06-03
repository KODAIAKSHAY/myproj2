package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.Event;

public interface EventDAO {
	public boolean save(Event event);
	public Event delete(int id);
	public boolean update(Event event);
	public List<Event> list();
	public Event get(int id);
}
