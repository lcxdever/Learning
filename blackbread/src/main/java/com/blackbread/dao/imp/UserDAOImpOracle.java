package com.blackbread.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.blackbread.dao.UserDAO;
import com.blackbread.model.User;

@Repository
public class UserDAOImpOracle implements UserDAO {

	public void query() {
		// TODO Auto-generated method stub

	}

	public List<Map<String, Object>> list() {
		System.out.println("由oracle db 来实现");
		return null;
	}

	public void add(List<User> list) {
		// TODO Auto-generated method stub

	}

}
