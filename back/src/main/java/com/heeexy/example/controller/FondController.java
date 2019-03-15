package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.heeexy.example.model.Article;
import com.heeexy.example.model.Fond;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.FondService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public PageInfo<Fond> getAll(Fond fond) {
        List<Fond> fondList = fondService.getAll(fond);
        return new PageInfo<Fond>(fondList);
    }

    /**
     * 新增全宗
     */
    @RequiresPermissions("fond:add")
    @PostMapping("/addFond")
    public ModelMap addArticle(@RequestBody Fond fond) {
        ModelMap result = new ModelMap();
        String msg = fond.getId() == null ? "新增成功!" : "更新成功!";
        fondService.save(fond);
        result.put("article", fond);
        result.put("msg", msg);
        return result;
    }

    /**
     * 修改全宗
     */
    @RequiresPermissions("fond:update")
    @PostMapping("/updateFond")
    public ModelMap updateFond(@RequestBody Fond fond) {
        ModelMap result = new ModelMap();
        String msg = fond.getId() == null ? "新增成功!" : "更新成功!";
        fondService.save(fond);
        result.put("article", fond);
        result.put("msg", msg);
        return result;
    }
}
