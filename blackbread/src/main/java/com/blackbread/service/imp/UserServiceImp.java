package com.blackbread.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.blackbread.dao.UserMapper;
import com.blackbread.model.User;
import com.blackbread.service.UserService;
import com.blackbread.utils.MapHelper;
import com.blackbread.utils.Pagination;
import com.blackbread.utils.UUID;
import com.blackbread.utils.encrypt.PwdUtil;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

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

	/**
	 * 使用/mybatis-paginator分页控件，参数只可为Map
	 */
	@SuppressWarnings("rawtypes")
	public Pagination query(Pagination pagination, User user) {
		String sortString = "CREATETIME.DESC";// 如果你想排序的话逗号分隔可以排序多列
		PageBounds pageBounds = new PageBounds(pagination.getPageNo(),
				pagination.getPageSize(), Order.formString(sortString));
		if(StringUtils.hasLength(user.getUserName()))
			user.setUserName("%"+user.getUserName()+"%");
		Map<String,Object> map=new HashMap<String, Object>();
		map=MapHelper.Bean2Map(user);
		List<User> list = userMapper.query(map, pageBounds);
		pagination.setItems(list);
		pagination.setTotalCount(((PageList) list).getPaginator().getTotalCount());
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
		return PwdUtil.validatePassword(user.getPassWord(), passWord,
				user.getSalt());
	}

}
