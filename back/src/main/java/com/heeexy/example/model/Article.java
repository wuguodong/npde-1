package com.heeexy.example.model;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * Created by lh on 2019-3-13.
 */
public class Article extends BaseEntity {
    private String content;

    private Timestamp createTime;

    private Timestamp updateTime;

    private String deleteStatus;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
