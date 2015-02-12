package com.blackbread.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackbread.dao.UserMapper;
import com.blackbread.model.User;
import com.blackbread.service.UserService;
import com.blackbread.utils.Pagination;
import com.blackbread.utils.UUID;
import com.blackbread.utils.encrypt.MD5;
import com.blackbread.utils.encrypt.PwdUtil;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	UserMapper userMapper;

	public void insert(User user) {
		user.setCreateTime(new Date());
		user.setId(UUID.getInstance().next());
		String salt = PwdUtil.genSalt();
		user.setSalt(salt);
		user.setPassWord(PwdUtil.genPwd(user.getPassWord(), salt));
		userMapper.insert(user);
	}

	public Pagination query(Pagination pagination, User user) {
		if (pagination == null)
			pagination = new Pagination(1, 10000);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pagination.getStart());
		map.put("end", pagination.getEnd());
		long totolCount = userMapper.count(map);
		pagination.setTotalCount(totolCount);
		List<User> list = userMapper.query(map);
		pagination.setValuesList(list);
		return pagination;
	}

	@Override
	public void modify(User user) {
		if (user.getPassWord() != null) {
			String salt = PwdUtil.genSalt();
			user.setSalt(salt);
			user.setPassWord(PwdUtil.genPwd(user.getPassWord(), salt));
		}
		userMapper.modify(user);
	}

	@Override
	public void delete(User user) {
		userMapper.delete(user);
	}

	@Override
	public boolean login(User user) {
		String passWord = user.getPassWord();
		user = userMapper.queryByID(user);
		if (user == null)
			return false;
		return PwdUtil.validatePassword(user.getPassWord(), passWord, user.getSalt());
	}

}
