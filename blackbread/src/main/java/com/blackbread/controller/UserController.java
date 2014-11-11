package com.blackbread.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackbread.model.User;
import com.blackbread.service.UserService;
import com.blackbread.utils.JsonUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(value = "/list/{type}")
	public String list(@PathVariable int type, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List<Map<String, Object>> list = userService.list(type);
		String json = JsonUtil.jsonFromObject(list);
//		for (Cookie cookie2 : request.getCookies()) {
//			System.out.println(cookie2.getName() + ":" + cookie2.getValue());
//		}
		// response.addCookie(cookie);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(@ModelAttribute("user") User user,
			HttpServletRequest request, ModelMap modelMap) {
		userService.save(user);
		return "success";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String query(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("name") String name) {
		request.setAttribute("aa", 123);
		if (name.equals("abc")) {
			throw new RuntimeException("出错了啊少年");
		}
		System.out.println(name);
		return "forward:/index.jsp";
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String query3(@PathVariable String name, Model model,
			ModelMap modelMap) {
		System.out.println(name);
		model.addAttribute("name");
		modelMap.put("abc", name);
		logger.error("ACL interceptor excueted. Accessible for Uri abc");
		return "redirect:/index.jsp";
	}

	@RequestMapping(params = "method=ajax")
	@ResponseBody
	public String ajax(ModelMap modelMap) {
		System.out.println(3333);
		modelMap.put("abc", 321);
		modelMap.put("cba", 234);
		return "{abc:123}";
	}

	@RequestMapping(value = "/{hang}/{lie}", method = RequestMethod.GET)
	@ResponseBody
	public String rest(@PathVariable String hang, @PathVariable String lie,
			Model model, ModelMap modelMap, HttpServletResponse response)
			throws IOException {
		System.out.println(hang + "," + lie);
		File file = new File(
				"D:/Program Files/Apache Software Foundation/Apache2.2/htdocs/basemap/L00/R00000125/C000000a9.png");
		response.setContentType("image/png");
		try {
			FileInputStream in = new FileInputStream(file);
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			if (in != null) {
				byte buffer[] = new byte[1024];
				do {
					int bytesRead = in.read(buffer, 0, 1024);
					if (bytesRead < 0) {
						break;
					}
					servletOutputStream.write(buffer, 0, bytesRead);// 将真实的内容原封不动的输出在浏览器
				} while (true);
			}
			servletOutputStream.flush();
			servletOutputStream.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
