package com.heeexy.example.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;

/**
 * Created by lh on 2019-3-13.
 */
@Table(name = "sys_user")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String nickname;
    private int roleId;
    private Timestamp createTime;
    private Timestamp updateTime;
    @Transient
    private String deleteStatus;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
