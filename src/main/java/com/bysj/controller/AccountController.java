package com.bysj.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.bysj.beans.User;
import com.bysj.service.UserService;
import com.bysj.tools.CommonUtils;
import com.bysj.tools.MsgHelper;

//账号信息管理类
@Controller
@RequestMapping("/client")
public class AccountController {

	@Resource
	private UserService userService;

	@Value("${UPLOAD_URL}")
	private String UPLOAD_URL;

	// 登录页面跳转
	@RequestMapping("/tologin")
	public String tologin(HttpServletRequest request, HttpSession session, String error, Map<String, Object> map) {
		session.setAttribute("c_user", null);
		session.setAttribute("error", error);
		return "client/login";
	}

	// 登录
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("usertype") String usertype, HttpSession session) {
		User user = userService.selectOne(
				new EntityWrapper<User>().eq("username", username).eq("password", password).eq("usertype", usertype));

		if (user == null) {
			return "redirect:/client/tologin?error=on";
		} else {
			session.setAttribute("c_user", user);
			return "redirect:/";
		}
	}

	// 退出登录
	@RequestMapping(value = { "loginout" })
	public String loginout(HttpSession session) {
		session.setAttribute("c_user", null);
		return "redirect:/";
	}

	// 跳转到注册页面
	@RequestMapping("/toregister")
	public String toregister(HttpServletRequest request, HttpSession session, Map<String, Object> map) {
		session.setAttribute("c_user", null);
		return "client/account/register";
	}

	// 提交注册信息
	@ResponseBody
	@RequestMapping("/register")
	public MsgHelper register(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile file, User bean) throws Exception {
		MsgHelper msgHelper = new MsgHelper("恭喜您注册成功！");
		if (StringUtils.isEmpty(bean.getUsername())) {
			msgHelper.setCode(-100);
			msgHelper.setMessage("用户名不能为空！");
			return msgHelper;
		}
		User user1 = userService.selectOne(new EntityWrapper<User>().eq("username", bean.getUsername()));
		if (user1 == null) {
			if (file != null) {
				File file2 = new File(UPLOAD_URL);
				if (!file2.exists()) {
					file2.mkdirs();
				}
				String extName = CommonUtils.getExtension(file.getOriginalFilename());
				String fileName = System.currentTimeMillis() + extName;
				String savePath = UPLOAD_URL + "/" + fileName;
				bean.setImagepath(fileName);
				file.transferTo(new File(savePath));
			}
			bean.setItem1("0");
			bean.setStatus("正常");
			bean.setCreatedate(CommonUtils.getNowDateTime());
			userService.insert(bean);
			// User user = userService.getLastOne();
			User user = userService.selectById(bean.getId());
			session.setAttribute("c_user", user);
			msgHelper.setUrl(request.getServletContext().getContextPath());
		} else {
			msgHelper.setCode(-100);
			msgHelper.setMessage("该用户名已经存在，请重新注册！");
		}
		return msgHelper;
	}

	// 跳转到修改密码页面
	@RequestMapping("/tochpwd")
	public String tochpwd(HttpServletRequest request, HttpSession session, Map<String, Object> map) {
		return "client/account/chpwd";
	}

	// 保存修改的密码
	@ResponseBody
	@RequestMapping(value = "/savepwd")
	public MsgHelper savepwd(@Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword,
			HttpSession session) throws Exception {
		MsgHelper msgHelper = new MsgHelper();
		User loginUser = (User) session.getAttribute("c_user");

		User user = userService.selectOne(new EntityWrapper<User>().eq("username", loginUser.getUsername())
				.eq("password", oldPassword).eq("usertype", loginUser.getUsertype()));

		if (user == null) {
			msgHelper.setMessage("原密码不正确！");
			msgHelper.setCode(500);
		} else {
			msgHelper.setMessage("密码修改成功，请重新登录！");
			loginUser.setPassword(newPassword);
			userService.updateById(loginUser);
		}
		return msgHelper;
	}

	@ModelAttribute
	public void getBean(HttpSession session, Map<String, Object> map) {
		User bean = (User) session.getAttribute("c_user");
		map.put("bean", bean);
	}

	// 我的个人信息
	@RequestMapping("/tomyinfo")
	public String tomyinfo(HttpServletRequest request, @ModelAttribute("bean") User bean, HttpSession session,
			Map<String, Object> map) {
		bean = (User) session.getAttribute("c_user");
		if (bean == null) {
			bean = new User();
		}
		map.put("bean", bean);
		return "client/account/myinfo";
	}

	// 保存我的个人信息
	@ResponseBody
	@RequestMapping("/savemyinfo")
	public MsgHelper savemyinfo(HttpSession session, @RequestParam(value = "file", required = false) MultipartFile file,
			User bean) throws Exception {

		MsgHelper msgHelper = new MsgHelper();
		User loginUser = (User) session.getAttribute("c_user");
		bean.setId(loginUser.getId());
		if (file != null) {
			File file2 = new File(UPLOAD_URL);
			if (!file2.exists()) {
				file2.mkdirs();
			}
			String extName = CommonUtils.getExtension(file.getOriginalFilename());
			String fileName = System.currentTimeMillis() + extName;
			String savePath = UPLOAD_URL + "/" + fileName;
			bean.setImagepath(fileName);
			file.transferTo(new File(savePath));
		}
		userService.updateById(bean);

		User user = userService.selectOne(new EntityWrapper<User>().eq("username", loginUser.getUsername())
				.eq("password", loginUser.getPassword()).eq("usertype", loginUser.getUsertype()));
		session.setAttribute("c_user", user);

		return msgHelper;
	}
}
