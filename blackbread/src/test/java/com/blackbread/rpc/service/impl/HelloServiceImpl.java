package com.blackbread.rpc.service.impl;

import com.blackbread.rpc.service.HelloService;

public class HelloServiceImpl implements HelloService {

	public String hello(String name) {
		return "Hello " + name;
	}

}