package com.npde.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.npde.example.model.ArchiveRoom;
import com.npde.example.model.BaseEntity;
import com.npde.example.model.Fond;
import com.npde.example.service.ArchiveRoomService;
import com.npde.example.util.CommonUtil;
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
@RequestMapping("/archiveRoom")
public class ArchiveRoomController {

    @Autowired
    private ArchiveRoomService archiveRoomService;

    /**
     * 查询文章列表
     */
    @RequiresPermissions("archiveRoom:list")
    @GetMapping("/listArchiveRoom")
    public JSONObject getAll(ArchiveRoom archiveRoom) {
        List<ArchiveRoom> archiveRoomList = archiveRoomService.getAll(archiveRoom);
        PageInfo<ArchiveRoom> blogPageInfo = new PageInfo<ArchiveRoom>(archiveRoomList);
        return CommonUtil.successJson(blogPageInfo);
    }

    /**
     * 新增文章
     */
    @RequiresPermissions("archiveRoom:add")
    @PostMapping("/addArchiveRoom")
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addArchiveRoom(@RequestBody ArchiveRoom archiveRoom) {
        archiveRoom.setEntityStatus(BaseEntity.ADD);
        archiveRoomService.operatorEntity(archiveRoom);
        return CommonUtil.successJson(archiveRoom);
    }

    /**
     * 修改档案库
     */
    @RequiresPermissions("archiveRoom:update")
    @PostMapping("/updateArchiveRoom")
    public JSONObject updateArchiveRoom(@RequestBody ArchiveRoom archiveRoom) {
        archiveRoom.setEntityStatus(BaseEntity.UPDATE);
        archiveRoomService.operatorEntity(archiveRoom);
        return CommonUtil.successJson(archiveRoom);
    }

    /**
     * 删除档案库
     */
    @RequiresPermissions("archiveRoom:delete")
    @PostMapping("/deleteArchiveRoom")
    public JSONObject deleteArchiveRoom(@RequestBody ArchiveRoom archiveRoom) {
        archiveRoom.setEntityStatus(BaseEntity.DELETE);
        archiveRoomService.operatorEntity(archiveRoom);
        return CommonUtil.successJson(archiveRoom);
    }
}
