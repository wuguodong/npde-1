package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.heeexy.example.model.Blog;
import com.heeexy.example.service.BlogService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: hxy
 * @description: 文章相关Controller
 * @date: 2017/10/24 16:04
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 查询文章列表
     */
    @RequiresPermissions("blog:list")
    @GetMapping("/listBlog")
    public JSONObject getAll(Blog blog) {
        List<Blog> blogs = blogService.getAll(blog);
        PageInfo<Blog> blogPageInfo =  new PageInfo<Blog>(blogs);
        return CommonUtil.successJson(blogPageInfo);
    }

    /**
     * 新增文章
     */
    @RequiresPermissions("blog:add")
    @PostMapping("/addBlog")
    @Transactional(rollbackFor = Exception.class)
    public ModelMap addBlog(@RequestBody Blog blog) {
        ModelMap result = new ModelMap();
        String msg = blog.getId() == null ? "新增成功!" : "更新成功!";
        blogService.save(blog);
        result.put("blog", blog);
        result.put("msg", msg);
        return result;
    }

    /**
     * 修改文章
     */
    @RequiresPermissions("blog:update")
    @PostMapping("/updateBlog")
    public ModelMap updateBlog(@RequestBody Blog blog) {
        ModelMap result = new ModelMap();
        String msg = blog.getId() == null ? "新增成功!" : "更新成功!";
        blogService.save(blog);
        result.put("blog", blog);
        result.put("msg", msg);
        return result;
    }
}
