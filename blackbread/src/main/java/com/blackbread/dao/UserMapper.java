package com.blackbread.dao;

import java.util.List;
import java.util.Map;

import com.blackbread.model.User;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface UserMapper {

	public List<User> query(Map<String,Object> map,PageBounds pageBounds);

	public long count(Map<String, Object> map);

	public void insert(User user);

	public void modify(User user);

	public void delete(User user);

	public User queryByID(User user);
}
