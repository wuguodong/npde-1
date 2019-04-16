package com.npde.example.service;

import com.github.pagehelper.PageHelper;
import com.npde.example.mapper.ArchiveRoomMapper;
import com.npde.example.model.ArchiveRoom;
import com.npde.example.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:07
 */
@Service
public class ArchiveRoomService {

    @Autowired
    private ArchiveRoomMapper archiveRoomMapper;


    public List<ArchiveRoom> getAll(ArchiveRoom archiveRoom) {
        if (archiveRoom.getPageNum() != null && archiveRoom.getPageRow() != null) {
            PageHelper.startPage(archiveRoom.getPageNum(), archiveRoom.getPageRow());
        }
        Example example = new Example(ArchiveRoom.class);
        Example.Criteria criteria = example.createCriteria();
        if (archiveRoom.getParentId() != null) {
            criteria.andEqualTo("parentId", archiveRoom.getParentId());
            return archiveRoomMapper.selectByExample(example);
        } else {
            criteria.andIsNull("parentId");
            return archiveRoomMapper.selectByExample(example);
        }
    }

    /**
     * 新增或者更新或者删除对应实体数据
     */
    public void operatorEntity(ArchiveRoom archiveRoom) {
        if (archiveRoom.getEntityStatus() == BaseEntity.UPDATE) {
            archiveRoomMapper.updateByPrimaryKey(archiveRoom);
        } else if (archiveRoom.getEntityStatus() == BaseEntity.ADD) {
            archiveRoomMapper.insert(archiveRoom);
        } else {
            archiveRoomMapper.deleteByPrimaryKey(archiveRoom.getId());
        }
    }
}
