package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heeexy.example.model.Article;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询文章列表
     */
    @RequiresPermissions("article:list")
    @GetMapping("/listArticle")
    public PageInfo<Article> getAll(Article article) {
        List<Article> articles = articleService.getAll(article);
        return new PageInfo<Article>(articles);
    }

    /**
     * 新增文章
     */
    @RequiresPermissions("article:add")
    @PostMapping("/addArticle")
    @Transactional(rollbackFor = Exception.class)
    public ModelMap addArticle(@RequestBody Article article) {
        ModelMap result = new ModelMap();
        String msg = article.getId() == null ? "新增成功!" : "更新成功!";
        articleService.save(article);
        result.put("article", article);
        result.put("msg", msg);
        return result;
    }

    /**
     * 修改文章
     */
    @RequiresPermissions("article:update")
    @PostMapping("/updateArticle")
    public ModelMap updateArticle(@RequestBody Article article) {
        ModelMap result = new ModelMap();
        String msg = article.getId() == null ? "新增成功!" : "更新成功!";
        articleService.save(article);
        result.put("article", article);
        result.put("msg", msg);
        return result;
    }
}
