package com.npde.example.model;

import javax.persistence.Transient;

/**
 * Created by lh on 2019-3-17.
 */
public class Organization extends BaseEntity {
    private String code;
    private String name;
    private String principal;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
