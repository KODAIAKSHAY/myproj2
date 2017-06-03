package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Blog;

public interface BlogDAO {
public boolean save(Blog blog);
public Blog delete(int id);
public boolean update(Blog blog);
public List<Blog> list();
public Blog get(int id);
}
