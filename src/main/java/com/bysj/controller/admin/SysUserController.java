package com.bysj.controller.admin;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bysj.beans.User;
import com.bysj.service.UserService;
import com.bysj.tools.CommonUtils;
import com.bysj.tools.MsgHelper;

//用户管理
@Controller
public class SysUserController {
	@Autowired
	UserService userService;

	@Value("${UPLOAD_URL}")
	private String UPLOAD_URL;

	// 账号管理
	@RequestMapping(value = "admin/user/index")
	public String index(HttpServletRequest request, HttpSession session, Map<String, Object> map) {
		return "admin/user/index";
	}

	// 账号管理
	@RequestMapping(value = "admin/user/getdata")
	public String getData(HttpServletRequest request, HttpSession session, @RequestParam Map<String, String> parameter,
			@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "5") Integer pageSize,
			Map<String, Object> map) throws Exception {

		// 0 后台 1 前台
		Page<User> list = userService.selectPage(new Page<User>(),
				new EntityWrapper<User>().eq("item1", "0").like("username", parameter.get("username")));
		map.put("list", list.getRecords());
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		map.put("itemTotal", list.getTotal());
		map.put("number", (pageIndex == 0 ? pageIndex : pageIndex - 1) * pageSize);
		return "/admin/user/getdata";
	}

	// 添加账号
	@RequestMapping(value = "admin/user/toadd")
	public String toAdd(HttpServletRequest request, HttpSession session, @Param("id") Integer id,
			Map<String, Object> map) {
		User bean = new User();
		if (id != null) {
			bean = userService.selectById(id);
		}
		map.put("bean", bean);
		return "admin/user/toadd";
	}

	@ModelAttribute
	public void getBean(HttpSession session, @RequestParam(value = "id", required = false) Integer id,
			Map<String, Object> map) {
		if (id != null) {
			User bean = userService.selectById(id);
			map.put("bean", bean);
		}
	}

	// 保存账号
	@ResponseBody
	@RequestMapping(value = "admin/user/save", method = RequestMethod.POST)
	public MsgHelper save(HttpSession session, @RequestParam(value = "file", required = false) MultipartFile file,
			@ModelAttribute("bean") User bean) throws Exception {
		MsgHelper msgHelper = new MsgHelper();

		User user = userService.selectOne(new EntityWrapper<User>().eq("username", bean.getUsername()));

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

		// 0 后台 1 前台
		bean.setItem1("0");
		bean.setStatus("正常");
		bean.setCreatedate(CommonUtils.getNowDateTime());
		if (bean.getId() == null)
			if (user != null) {
				msgHelper.setCode(300);
				msgHelper.setMessage("该账号已经存在，不能重复添加！");
			} else {
				userService.insert(bean);
			}
		else {
			if (user != null && user.getId() != bean.getId()) {
				msgHelper.setCode(300);
				msgHelper.setMessage("该账号已经存在，不能重复添加！");
			} else {
				userService.updateById(bean);
			}
		}
		return msgHelper;
	}

	// 删除账号
	@ResponseBody
	@RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST)
	public MsgHelper delete(Integer id) {
		MsgHelper msgHelper = new MsgHelper();
		userService.deleteById(id);
		return msgHelper;
	}

	// 我的个人信息
	@RequestMapping(value = "admin/user/myinfo")
	public String toMyInfo(HttpServletRequest request, Map<String, Object> map, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		User tUser = userService.selectById(user.getId());
		map.put("bean", tUser);
		return "admin/user/myinfo";
	}

	// 保存我的个人信息
	@ResponseBody
	@RequestMapping(value = "admin/user/savemyinfo")
	public MsgHelper saveMyInfo(Map<String, Object> map, HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute("bean") User bean)
			throws Exception {
		MsgHelper msgHelper = new MsgHelper();
		User user = (User) session.getAttribute("user");
		bean.setId(user.getId());

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
		return msgHelper;
	}

	// 修改密码
	@RequestMapping(value = "admin/user/tochpwd")
	public String toChpwd(Map<String, Object> map, HttpSession session) throws Exception {
		User loginUser = (User) session.getAttribute("user");
		map.put("user", loginUser);
		return "admin/user/chpwd";
	}

	// 保存修改密码
	@ResponseBody
	@RequestMapping(value = "admin/user/chpwd")
	public MsgHelper chpwd(@Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword,
			HttpSession session) throws Exception {
		MsgHelper msgHelper = new MsgHelper();
		User loginUser = (User) session.getAttribute("user");

		User user = userService.selectOne(new EntityWrapper<User>().eq("username", loginUser.getUsername())
				.eq("password", oldPassword).eq("usertype", loginUser.getUsertype()));
		if (user == null) {
			msgHelper.setMessage("原密码不正确！");
		} else {
			loginUser.setPassword(newPassword);
			userService.updateById(loginUser);
		}
		return msgHelper;
	}

	// 用户管理
	@RequestMapping(value = "admin/user/indexregister")
	public String indexregister(HttpServletRequest request, HttpSession session, Map<String, Object> map) {
		return "admin/user/indexregister";
	}

	// 用户管理
	@RequestMapping(value = "admin/user/getdataregister")
	public String getDataregister(HttpServletRequest request, HttpSession session,
			@RequestParam Map<String, String> parameter, @RequestParam(defaultValue = "1") Integer pageIndex,
			@RequestParam(defaultValue = "5") Integer pageSize, Map<String, Object> map) throws Exception {

		Page<User> list = userService.selectPage(new Page<User>(),
				new EntityWrapper<User>().eq("item1", "1").like("username", parameter.get("username")));
		// 创建查询条件
		map.put("list", list.getRecords());
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		map.put("itemTotal", list.getTotal());
		map.put("number", (pageIndex == 0 ? pageIndex : pageIndex - 1) * pageSize);
		System.out.println("---------------------------"+list);
		return "/admin/user/getdataregister";
	}

}
