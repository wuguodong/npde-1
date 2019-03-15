package com.heeexy.example.model;

import java.io.Serializable;

/**
 * Created by lh on 2019-3-13.
 */
public class Fond extends BaseEntity {
    private String name;
    private int parentId;
    private int isStorage;
    private String desc;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(int isStorage) {
        this.isStorage = isStorage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
