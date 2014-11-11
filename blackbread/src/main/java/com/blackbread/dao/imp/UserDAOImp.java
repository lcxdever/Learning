package com.blackbread.dao.imp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blackbread.dao.UserDAO;
import com.blackbread.model.User;

@Repository
public class UserDAOImp implements UserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> list() {
		String sql = "select * from flow_tname_manager";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	public void add(final List<User> list) {
		String sqlStr = "insert into  iuser (id,name,password)values(?,?,?)";
		jdbcTemplate.batchUpdate(sqlStr, new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return list.size();
			}

			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setLong(1, list.get(i).getId());
				ps.setString(2, list.get(i).getUsername());
				ps.setString(3, list.get(i).getPassword());
			}
		});
	}

	public void query() {
		// TODO Auto-generated method stub

	}

}
