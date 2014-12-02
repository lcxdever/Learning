package com.blackbread.rpc;

import com.blackbread.rpc.service.HelloService;
import com.blackbread.rpc.service.impl.HelloServiceImpl;

public class Provider {

	public static void main(String[] args) throws Exception {
		HelloService service = new HelloServiceImpl();
		RpcFramework.export(service, 1234);
	}
}
