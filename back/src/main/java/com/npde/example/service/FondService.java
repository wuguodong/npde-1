package com.npde.example.service;

import com.github.pagehelper.PageHelper;
import com.npde.example.model.BaseEntity;
import com.npde.example.model.Fond;
import com.npde.example.mapper.FondMapper;
import com.npde.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
@Service
public class FondService {
    @Autowired
    private FondMapper fondMapper;

    public List<Fond> getAll(Fond fond) {
        if (fond.getPageNum() != null && fond.getPageRow() != null) {
            PageHelper.startPage(fond.getPageNum(), fond.getPageRow());
        }
        return fondMapper.selectAll();
    }

    public Fond getById(Integer id) {
        return fondMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        fondMapper.deleteByPrimaryKey(id);
    }

    public void save(Fond fond) {
        if (fond.getEntityStatus().equals(BaseEntity.ADD)) {
            fondMapper.insert(fond);
        } else if (fond.getEntityStatus().equals(BaseEntity.UPDATE)) {
            fondMapper.updateByPrimaryKey(fond);
        } else if (fond.getEntityStatus().equals(BaseEntity.DELETE)) {
            fondMapper.deleteByPrimaryKey(fond.getId());
        }
    }


    public List<Fond> selectFondsByUser(User user) {
        return fondMapper.selectFondsByUser(user);
    }
}
