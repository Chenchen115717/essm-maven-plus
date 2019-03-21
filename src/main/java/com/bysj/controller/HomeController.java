package com.bysj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//首页
@Controller
public class HomeController extends ClientBaseController {

	@RequestMapping(value = { "/", "/client" })
	public String index(HttpSession session, Map<String, Object> map) {
		return "index";
	}

	@RequestMapping(value = "/index.shtml")
	public String indexshtml(HttpServletRequest request, HttpSession session,
			@RequestParam Map<String, String> parameter, Map<String, Object> map,
			@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "5") Integer pageSize) {
		return "client/index";
	}
}
