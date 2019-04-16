package com.npde.example.mapper;

import com.alibaba.fastjson.JSONObject;
import com.npde.example.model.User;
import com.npde.example.util.MyMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: hxy
 * @description: 登录相关dao
 * @date: 2017/10/24 11:02
 */
public interface LoginMapper extends MyMapper<User>{
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	JSONObject getUser(@Param("username") String username, @Param("password") String password);
}