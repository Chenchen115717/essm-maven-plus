package com.bysj.controller.admin;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.bysj.beans.User;
import com.bysj.service.UserService;

//登录管理
@RequestMapping("/admin/")
@Controller
public class SysLoginController {

	@Resource
	private UserService userService;

	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpSession session, String error, Map<String, Object> map) {
		session.setAttribute("user", null);
		session.setAttribute("error", error);
		return "/admin/login";
	}

	@RequestMapping("loginExpire")
	public String loginExpire() {
		return "/admin/loginExpire";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("usertype") String usertype, HttpSession session) {
		User user = userService.selectOne(
				new EntityWrapper<User>().eq("username", username).eq("password", password).eq("usertype", usertype));

		if (user == null) {
			return "redirect:/admin/login?error=on";
		} else {
			session.setAttribute("user", user);
			return "redirect:/admin/index";
		}
	}
}
