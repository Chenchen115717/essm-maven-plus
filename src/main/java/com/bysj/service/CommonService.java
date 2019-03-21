package com.bysj.service;

import com.github.pagehelper.PageInfo;

public interface CommonService<T> {

	PageInfo<T> getAllBySql(Class<?> classzz,String sql);
	
	PageInfo<T> getAllBySql(Class<?> classzz, String sql, int pageIndex, int pageSize);

	T getOne(Class<?> classzz,String sql);
}
