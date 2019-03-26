package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.heeexy.example.model.BaseEntity;
import com.heeexy.example.model.Organization;
import com.heeexy.example.service.OrganizationService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lh on 2019-3-17.
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    /**
     * 查询全宗列表
     */
    @RequiresPermissions("organization:list")
    @GetMapping("/listOrganization")
    public JSONObject getAll(Organization organization) {
        List<Organization> organizationList = organizationService.getAll(organization);
        PageInfo<Organization> PageInfo = new PageInfo<Organization>(organizationList);
        return CommonUtil.successJson(PageInfo);
    }

    /**
     * 查询全宗列表
     */
    //@RequiresPermissions("organization:list")
    //@GetMapping("/listUserOrganization")
    //public JSONObject getUserOrganization(User user) {
    //    List<Organization> organizationList = organizationService.selectOrganizationByUser(user);
    //    PageInfo<Organization:> Organization:PageInfo = new PageInfo<Organization:>(Organization:List);
    //    return CommonUtil.successJson(Organization:PageInfo);
    //}

    /**
     * 新增全宗
     */
    @RequiresPermissions("organization:add")
    @PostMapping("/addOrganization")
    public JSONObject addOrganization(@RequestBody Organization organization) {
        organization.setEntityStatus(BaseEntity.ADD);
        organizationService.save(organization);
        return CommonUtil.successJson(organization);
    }

    /**
     * 修改全宗
     */
    @RequiresPermissions("organization:update")
    @PostMapping("/updateOrganization")
    public JSONObject updateOrganization(@RequestBody Organization organization) {
        organization.setEntityStatus(BaseEntity.UPDATE);
        organizationService.save(organization);
        return CommonUtil.successJson(organization);
    }

    /**
     * 删除全宗
     */
    @RequiresPermissions("organization:delete")
    @PostMapping("/deleteOrganization")
    public JSONObject deleteOrganization(@RequestBody Organization organization) {
        organization.setEntityStatus(BaseEntity.DELETE);
        organizationService.save(organization);
        return CommonUtil.successJson(organization);
    }
}
