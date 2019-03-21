package com.bysj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bysj.beans.Product;
import com.bysj.service.CommonService;
import com.bysj.service.ProductService;
import com.github.pagehelper.PageInfo;

//商品管理类
@RequestMapping("/client")
@Controller
public class ProductController extends ClientBaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CommonService<Product> commonService;

	// 管理页面
	@RequestMapping(value = "/product/index")
	public String index(HttpServletRequest request, HttpSession session, @Param("cid") Integer cid,
			@Param("titlexxx") String titlexxx, @Param("type") String type, Map<String, Object> map) {

		map.put("cid", cid);
		map.put("titlexxx", titlexxx);
		map.put("type", type);

		return "client/product/index";
	}

	// 获取数据
	@RequestMapping(value = "/product/getdata")
	public String getData(HttpServletRequest request, HttpSession session, @Param("cid") String cid,
			@Param("titlexxx") String titlexxx, @Param("type") String type, Map<String, Object> map,
			@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "8") Integer pageSize)
			throws Exception {
		String sql = "SELECT a.*,b.username FROM t_product AS a LEFT JOIN t_user AS b ON a.userid = b.id where true ";

		if (StringUtils.isNotBlank(titlexxx)) {
			sql += " and (a.title like '%" + titlexxx + "%' OR a.originalname like '%" + titlexxx + "%')";
		}
		sql += " ORDER BY a.id DESC ";
		
		PageInfo<Product> list = commonService.getAllBySql(Product.class, sql, pageIndex, pageSize);
		map.put("list", list.getList());
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		map.put("itemTotal", list.getTotal());
		map.put("number", (pageIndex == 0 ? pageIndex : pageIndex - 1) * pageSize);
		map.put("type", type);
		return "client/product/getdata";
	}

	// 获取详情
	@RequestMapping(value = "/product/getone")
	public String getone(HttpServletRequest request, HttpSession session, @Param("id") Integer id,
			@RequestParam Map<String, String> parameter, Map<String, Object> map) {
		String sql = "SELECT a.*,b.username FROM t_product AS a LEFT JOIN t_user AS b ON a.userid = b.id where a.id = "+id;
		Product bean = commonService.getOne(Product.class, sql);
		map.put("bean", bean);

		return "client/product/getone";
	}
}
