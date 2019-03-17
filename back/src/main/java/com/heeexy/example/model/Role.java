package com.heeexy.example.model;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by lh on 2019-3-13.
 */
public class Role extends BaseEntity {
    private String roleName;
    private Timestamp createTime;

    private Timestamp updateTime;

    private String deleteStatus;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
