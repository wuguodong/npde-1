package com.heeexy.example.model;

import java.sql.Timestamp;

/**
 * Created by lh on 2019-3-13.
 */
public class RolePermission extends BaseEntity {
    private String username;
    private String role_id;
    private String permission_id;
    private Timestamp create_time;
    private Timestamp update_time;
    private String delete_status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }
}
