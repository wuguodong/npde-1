package com.heeexy.example.service;

import com.github.pagehelper.PageHelper;
import com.heeexy.example.mapper.FondMapper;
import com.heeexy.example.mapper.FondPermissionMapper;
import com.heeexy.example.model.BaseEntity;
import com.heeexy.example.model.Fond;
import com.heeexy.example.model.FondPermission;
import com.heeexy.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: lh
 * @date: 2019/04/08 6:06
 */
@Service
public class FondPermissionService {
    @Autowired
    private FondPermissionMapper fondPermissionMapper;

    public List<FondPermission> getAll(FondPermission fondPermission) {
        if (fondPermission.getPageNum() != null && fondPermission.getPageRow() != null) {
            PageHelper.startPage(fondPermission.getPageNum(), fondPermission.getPageRow());
        }
        return fondPermissionMapper.selectAll();
    }

    public FondPermission getById(Integer id) {
        return fondPermissionMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        fondPermissionMapper.deleteByPrimaryKey(id);
    }

    public void updateFondDataRole(List<FondPermission> fondPermissionList) {
        for (FondPermission fondPermission : fondPermissionList) {
            if (null != fondPermissionMapper.selectByPrimaryKey(fondPermission.getFondId())) {
                fondPermissionMapper.updateByPrimaryKey(fondPermission);
            } else {
                fondPermissionMapper.insert(fondPermission);
            }
        }
    }

    public List<FondPermission> selectFondsByRole(FondPermission fondPermission) {
        Example example = new Example(FondPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", fondPermission.getRoleId());
        criteria.andEqualTo("fondId", fondPermission.getFondId());
        return fondPermissionMapper.selectByExample(example);
    }
}
