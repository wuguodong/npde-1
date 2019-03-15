package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.mapper.LoginMapper;
import com.heeexy.example.model.User;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;

/**
 * @author: hxy
 * @description: 登录service实现类
 * @date: 2017/10/24 11:53
 */
@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private PermissionService permissionService;

    /**
     * 登录表单提交
     */
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            info.put("result", "success");
        } catch (AuthenticationException e) {
            info.put("result", "fail");
        }
        return CommonUtil.successJson(info);
    }

    /**
     * 根据用户名和密码查询对应的用户
     */
    public User getUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User user1 = loginMapper.selectOne(user);
        return user1;
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        User userInfo = (User) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getUsername();
        JSONObject info = new JSONObject();
        JSONObject userPermission = permissionService.getUserPermission(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
        info.put("userPermission", userPermission);
        return CommonUtil.successJson(info);
    }

    /**
     * 退出登录
     */
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtil.successJson();
    }
}
