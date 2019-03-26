package com.heeexy.example.model;

/**
 * Created by lh on 2019-3-13.
 * 全宗实体
 */
public class Fond extends BaseEntity {
    private String fondName;
    private String parentName;
    private int storage;
    private String fondDesc;

    public String getFondName() {
        return fondName;
    }

    public void setFondName(String fondName) {
        this.fondName = fondName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getFondDesc() {
        return fondDesc;
    }

    public void setFondDesc(String fondDesc) {
        this.fondDesc = fondDesc;
    }
}
