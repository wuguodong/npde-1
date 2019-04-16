package com.npde.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.npde.example.model.Fond;
import com.npde.example.model.FondPermission;
import com.npde.example.model.User;
import com.npde.example.service.FondPermissionService;
import com.npde.example.service.FondService;
import com.npde.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: hxy
 * @description: 文章相关Controller
 * @date: 2017/10/24 16:04
 */
@RestController
@RequestMapping("/fond")
public class FondController {

    @Autowired
    private FondService fondService;

    @Autowired
    FondPermissionService fondPermissionService;

    /**
     * 查询全宗列表
     */
    @RequiresPermissions("fond:list")
    @GetMapping("/listFond")
    public JSONObject getAll(Fond fond) {
        List<Fond> fondList = fondService.getAll(fond);
        PageInfo<Fond> fondPageInfo = new PageInfo<>(fondList);
        return CommonUtil.successJson(fondPageInfo);
    }


    /**
     * 查询全宗列表
     */
    @RequiresPermissions("role:list")
    @PostMapping("/listFondPermission")
    public JSONObject getFondPermissionWithRole(@RequestBody FondPermission fondPermission) {
        List<FondPermission> fondPermissionList = fondPermissionService.selectFondsByRole(fondPermission);
        return CommonUtil.successJson(fondPermissionList);
    }

    /**
     * 根据用户查找用户被授权访问的全宗列表
     */
    @RequiresPermissions("fond:list")
    @GetMapping("/listUserFond")
    public JSONObject getUserFond(User user) {
        List<Fond> fondList = fondService.selectFondsByUser(user);
        PageInfo<Fond> fondPageInfo = new PageInfo<>(fondList);
        return CommonUtil.successJson(fondPageInfo);
    }

    /**
     * 新增全宗
     */
    @RequiresPermissions("fond:add")
    @PostMapping("/addFond")
    public JSONObject addFond(@RequestBody Fond fond) {
        fondService.save(fond);
        return CommonUtil.successJson(fond);
    }

    /**
     * 修改全宗
     */
    @RequiresPermissions("fond:update")
    @PostMapping("/updateFond")
    public JSONObject updateFond(@RequestBody Fond fond) {
        fondService.save(fond);
        return CommonUtil.successJson(fond);
    }


    /**
     * 修改某个角色对某个具体全宗的操作权限
     */
    @RequiresPermissions("role:update")
    @PostMapping("/updateFondDataRole")
    public JSONObject updateFondDataRole(@RequestBody List<FondPermission> fondPermissionList) {
        fondPermissionService.updateFondDataRole(fondPermissionList);
        return CommonUtil.successJson(fondPermissionList);
    }


    /**
     * 修改某个角色对某个具体全宗的操作权限
     */
    @RequiresPermissions("role:update")
    @PostMapping("/removeAllFondPermission")
    public JSONObject removeAllFondDataRole(@RequestBody List<FondPermission> fondPermissionList) {
        fondPermissionService.removeAllFondDataRole(fondPermissionList);
        return CommonUtil.successJson(fondPermissionList);
    }

    /**
     * 删除全宗
     */
    @RequiresPermissions("fond:delete")
    @PostMapping("/deleteFond")
    public JSONObject deleteFond(@RequestBody Fond fond) {
        fondService.deleteById(fond.getId());
        return CommonUtil.successJson(fond);
    }
}
