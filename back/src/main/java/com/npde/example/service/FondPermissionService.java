package com.npde.example.service;

import com.github.pagehelper.PageHelper;
import com.npde.example.mapper.FondPermissionMapper;
import com.npde.example.model.FondPermission;
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
            if (this.selectFondsByRole(fondPermission).size() > 0) {
                if (fondPermission.getDeleteStatus().equals("0")) {
                    Example example = new Example(FondPermission.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("roleId", fondPermission.getRoleId());
                    criteria.andEqualTo("permissionId", fondPermission.getPermissionId());
                    criteria.andEqualTo("fondId", fondPermission.getFondId());
                    fondPermission.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
                    fondPermissionMapper.updateByExampleSelective(fondPermission, example);
                }
            } else {
                //先插入所有权限条目，在更新
                this.insertAllPermissinWithNoPermission(fondPermissionList);
                this.updateFondDataRole(fondPermissionList);
            }
        }
    }

    public void removeAllFondDataRole(List<FondPermission> fondPermissionList) {
        for (FondPermission fondPermission : fondPermissionList) {
            if (this.selectFondsByRole(fondPermission).size() > 0) {
                Example example = new Example(FondPermission.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("roleId", fondPermission.getRoleId());
                criteria.andEqualTo("permissionId", fondPermission.getPermissionId());
                criteria.andEqualTo("fondId", fondPermission.getFondId());
                fondPermission.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
                fondPermission.setDeleteStatus("1");
                fondPermissionMapper.updateByExampleSelective(fondPermission, example);
            }
        }
    }

    private List<FondPermission> insertAllPermissinWithNoPermission(List<FondPermission> fondPermissionList) {
        for (FondPermission fondPermission : fondPermissionList) {
            //先插入所有权限条目，在更新
            FondPermission fondPermissionTemp = new FondPermission();
            fondPermissionTemp.setFondId(fondPermission.getFondId());
            fondPermissionTemp.setRoleId(fondPermission.getRoleId());
            fondPermissionTemp.setPermissionId(fondPermission.getPermissionId());
            fondPermissionTemp.setPermissionName(fondPermission.getPermissionName());
            fondPermissionTemp.setDeleteStatus("1");
            fondPermissionTemp.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
            fondPermissionTemp.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
            fondPermissionMapper.insert(fondPermissionTemp);
        }
        return fondPermissionList;
    }

    ;

    public List<FondPermission> selectFondsByRole(FondPermission fondPermission) {
        Example example = new Example(FondPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", fondPermission.getRoleId());
        criteria.andEqualTo("fondId", fondPermission.getFondId());
        return fondPermissionMapper.selectByExample(example);
    }
}
