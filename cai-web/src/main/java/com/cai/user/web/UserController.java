package com.cai.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cai.user.domains.User;
import com.cai.user.service.UserService;
import com.cai.utils.MD5Util;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService UserService;

	@RequestMapping(path = "/login")
	public String login(User user) {
		String name = user.getName();
		User result = UserService.findUser(name);
		if (result != null) {
			if (result.getPassword().equals(MD5Util.MD5Encode(user.getPassword()))) {

				return "page.user.success";
			}

		} else {

		}

		return "page.user.login";
	}

	@RequestMapping(path = "/view")
	public String view() {
		return "page.user.login";
	}

}
