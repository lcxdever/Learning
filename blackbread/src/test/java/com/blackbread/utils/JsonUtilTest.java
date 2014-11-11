package com.blackbread.utils;

import java.util.List;
import java.util.Map;

import org.junit.Test;

@SuppressWarnings("rawtypes")
public class JsonUtilTest {
	@Test
	public void objectFromJson_TEST() {
		String jstr = "[{\"id\":3,\"t_name\":\"位置智能\",\"t_id\":1953904727,\"access_token\":\"6200509d70fdf2c08ZZ5f75f7ce2100def333866a099aec1953904727\",\"refresh_token\":\"62015093a6ee649f1ZZbf0f23bbe50c51d22d83adefbeb71953904727\",\"access_time_out\":1407405547343,\"refresh_time_out\":1407405547343}]";
		List<Map> list = JsonUtil.objectFromJson(jstr, List.class);
		for (Map map : list) {
			System.out.println(map);
		}
	}
}
