package com.heeexy.example.service;

import com.github.pagehelper.PageHelper;
import com.heeexy.example.model.Fond;
import com.heeexy.example.mapper.FondMapper;
import com.heeexy.example.model.User;
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
        if (fond.getId() != null) {
            Fond f = fondMapper.selectByPrimaryKey(fond.getOldId());
            if (null != f) {
                fondMapper.updateByPrimaryKey(fond);
            } else {
                fondMapper.insert(fond);
            }
        } else {
            fondMapper.insert(fond);
        }
    }


    public List<Fond> selectFondsByUser(User user) {
        return fondMapper.selectFondsByUser(user);
    }
}
