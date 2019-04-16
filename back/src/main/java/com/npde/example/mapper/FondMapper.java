package com.npde.example.mapper;

import com.alibaba.fastjson.JSONObject;
import com.npde.example.model.Fond;
import com.npde.example.model.User;
import com.npde.example.util.MyMapper;

import java.util.List;

/**
 * @author: hxy
 * @description: 全宗Dao层
 * @date: 2017/10/24 16:06
 */
public interface FondMapper extends MyMapper<Fond> {
    /**
     * 根据用户查找用户授权的全宗列表
     */
    List<Fond> selectFondsByUser(User user);
}
