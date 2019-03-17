package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.heeexy.example.model.Fond;
import com.heeexy.example.model.User;
import com.heeexy.example.service.FondService;
import com.heeexy.example.util.CommonUtil;
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

    /**
     * 查询全宗列表
     */
    @RequiresPermissions("fond:list")
    @GetMapping("/listFond")
    public JSONObject getAll(Fond fond) {
        List<Fond> fondList = fondService.getAll(fond);
        PageInfo<Fond> fondPageInfo = new PageInfo<Fond>(fondList);
        return CommonUtil.successJson(fondPageInfo);
    }

    /**
     * 查询全宗列表
     */
    @RequiresPermissions("fond:list")
    @GetMapping("/listUserFond")
    public JSONObject getUserFond(User user) {
        List<Fond> fondList = fondService.selectFondsByUser(user);
        PageInfo<Fond> fondPageInfo = new PageInfo<Fond>(fondList);
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
}
