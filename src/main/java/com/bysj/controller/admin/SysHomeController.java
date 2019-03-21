package com.bysj.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台主页
 * 
 * @author Administrator
 *
 */
@Controller
public class SysHomeController{

	@RequestMapping(value = "/admin/index")
	public String index(HttpServletRequest request) throws Exception {
		return "admin/index";
	}
}
