package com.bysj.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.bysj.beans.User;

//前台公共基础类
@Controller
public class ClientBaseController {

	// 获取当前登录用户
	public User getUser(HttpSession session) {
		Object bean = session.getAttribute("c_user");
		if (bean != null) {
			return (User) bean;
		}
		return null;
	}

	// 获取当前登录用户的ID
	public Integer getUserId(HttpSession session) {
		if (getUser(session) != null) {
			return getUser(session).getId();
		}
		return null;
	}
}
