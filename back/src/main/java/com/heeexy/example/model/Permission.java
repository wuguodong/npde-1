package com.heeexy.example.model;

import java.sql.Timestamp;

/**
 * Created by lh on 2019-3-13.
 */
public class Permission extends BaseEntity {
    private String menu_code;
    private String menu_name;
    private String permission_code;
    private String permission_name;
    private int required_permission;


    public String getMenu_code() {
        return menu_code;
    }

    public void setMenu_code(String menu_code) {
        this.menu_code = menu_code;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getPermission_code() {
        return permission_code;
    }

    public void setPermission_code(String permission_code) {
        this.permission_code = permission_code;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public int getRequired_permission() {
        return required_permission;
    }

    public void setRequired_permission(int required_permission) {
        this.required_permission = required_permission;
    }
}
