package com.blackbread.beans;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.blackbread.dao.UserDAO;

public class Beans {
	@Test
	public void Test() {

		Resource resource = new ClassPathResource("application.xml");
		ListableBeanFactory factory = new XmlBeanFactory(resource);

		UserDAO userDAO =(UserDAO)factory.getBean("userDAO");
	}

}
