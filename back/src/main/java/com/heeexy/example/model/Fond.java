package com.heeexy.example.model;

import java.io.Serializable;

/**
 * Created by lh on 2019-3-13.
 */
public class Fond extends BaseEntity {
    private String fondName;
    private int parentId;
    private int isStorage;
    private String fondDesc;

    public String getFondName() {
        return fondName;
    }

    public void setFondName(String fondName) {
        this.fondName = fondName;
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

    public String getFondDesc() {
        return fondDesc;
    }

    public void setFondDesc(String fondDesc) {
        this.fondDesc = fondDesc;
    }
}
