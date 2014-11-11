package com.blackbread.service;

import java.util.List;
import java.util.Map;

import com.blackbread.model.User;

public interface UserService {
	public void save(User user);

	public List<Map<String, Object>> list(int type);
}
