package com.blackbread.service;

import com.blackbread.model.User;
import com.blackbread.utils.Pagination;

public interface UserService {
	public void insert(User user);

	public Pagination query(Pagination pagination, User user);

	public void modify(User user);

	public void delete(User user);

	public boolean login(User user);
}
