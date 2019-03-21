package com.bysj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommonMapper<T> {

	List<Map<String, Object>> getAllBySql(@Param(value = "sql") String sql);

	Map<String, Object> getOne(@Param(value = "sql") String sql);
}