package com.blackbread.dao;

import java.util.List;
import java.util.Map;

import com.blackbread.model.User;

public interface UserDAO {
	public void query();

	public List<Map<String, Object>> list();

	public void add(final List<User> list);

}
