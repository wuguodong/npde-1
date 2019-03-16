package com.heeexy.example.model;

import javax.persistence.Column;
import java.sql.Date;

/**
 * Created by lh on 2019-3-13.
 */
public class Blog extends BaseEntity {
    private String content;
    private String createTime;
    private String  updateTime;
    private String deleteStatus;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
