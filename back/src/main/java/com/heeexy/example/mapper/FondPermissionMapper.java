package com.heeexy.example.mapper;

import com.heeexy.example.model.Fond;
import com.heeexy.example.model.FondPermission;
import com.heeexy.example.model.User;
import com.heeexy.example.util.MyMapper;

import java.util.List;

/**
 * @author: hxy
 * @description: 全宗Dao层
 * @date: 2017/10/24 16:06
 */
public interface FondPermissionMapper extends MyMapper<FondPermission> {
    /**
     * 根据用户查找用户授权的全宗列表
     */
    List<FondPermission> selectFondsByUser(User user);
}
