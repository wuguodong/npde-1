package com.heeexy.example.service;

import com.github.pagehelper.PageHelper;
import com.heeexy.example.mapper.OrganizationMapper;
import com.heeexy.example.model.Fond;
import com.heeexy.example.model.Organization;
import com.heeexy.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
@Service
public class OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;

    public List<Organization> getAll(Organization organization) {
        if (organization.getPageNum() != null && organization.getPageRow() != null) {
            PageHelper.startPage(organization.getPageNum(), organization.getPageRow());
        }
        return organizationMapper.selectAll();
    }

    public Organization getById(Integer id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        organizationMapper.deleteByPrimaryKey(id);
    }

    public void save(Organization organization) {
        if (organization.getId() != null) {
            Organization o = organizationMapper.selectByPrimaryKey(organization.getOldId());
            if (null != o) {
                organizationMapper.updateByPrimaryKey(organization);
            } else {
                organizationMapper.insert(organization);
            }
        } else {
            organizationMapper.insert(organization);
        }
    }
}
