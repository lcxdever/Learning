package com.blackbread.webservice;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@WebService
public class Hello {
	// http://localhost:8080/blackbread/webservice/soap/getHello?arg0=abc
	public String getHello(String name) {
		return "Hello， " + name + ".";
	}

	// http://localhost:8080/blackbread/webservice/soap/getWorld?arg0=add
	public String getWorld(String name) {
		return "World," + name + ".";
	}

	@GET
	@Path(value = "/request/{param}")
	public String sayHello(@PathParam(value = "param")
	String name) {
		return "Hello," + name + "******";
	}
	// http://localhost:8080/blackbread/webservice/rest/request/aabbb
}
