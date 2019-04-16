package com.npde.example.model;

import javax.persistence.*;

/**
 * Created by lh on 2019-3-13.
 */
public class BaseEntity {

    public  static final String DELETE = "delete";
    public  static final String UPDATE = "update";
    public  static final String ADD = "add";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private String entityStatus;

    @Transient
    private Integer pageNum = 1;

    @Transient
    private Integer pageRow = 50;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageRow() {
        return pageRow;
    }

    public void setPageRow(Integer pageRow) {
        this.pageRow = pageRow;
    }


    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }
}
