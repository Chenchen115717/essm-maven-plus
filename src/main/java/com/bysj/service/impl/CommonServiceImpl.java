package com.bysj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bysj.mapper.CommonMapper;
import com.bysj.service.CommonService;
import com.bysj.tools.ReflectUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CommonServiceImpl<T> implements CommonService<T> {

	@Autowired
	private CommonMapper<T> mapper;

	@Override
	public PageInfo<T> getAllBySql(Class<?> classzz, String sql) {
		List<Map<String, Object>> list = mapper.getAllBySql(sql);
		List<T> list2 = new ReflectUtils<T>().toList(classzz, list);
		if (list2 == null) {
			return new PageInfo<>();
		}
		return new PageInfo<>(list2);
	}

	@Override
	public PageInfo<T> getAllBySql(Class<?> classzz, String sql, int pageIndex, int pageSize) {
		boolean startPage = false;
		if (pageIndex > 0 && pageSize > 0) {
			PageHelper.startPage(pageIndex, pageSize);
			startPage = true;
		} else if (pageIndex == -1 && pageSize > 0) {
			sql += " LIMIT " + pageSize;
		}

		List<Map<String, Object>> list = mapper.getAllBySql(sql);
		List<T> list2 = new ReflectUtils<T>().toList(classzz, list);
		if (list2 == null) {
			return new PageInfo<>();
		}
		if (startPage) {
			Page<T> page = (Page<T>) list;
			PageInfo<T> pageInfo = page.toPageInfo();
			pageInfo.setList(list2);
			return pageInfo;
		} else {
			return new PageInfo<>(list2);
		}
	}

	@Override
	public T getOne(Class<?> classzz, String sql) {
		Map<String, Object> map = mapper.getOne(sql);
		return new ReflectUtils<T>().toModel(classzz, map);
	}
}
