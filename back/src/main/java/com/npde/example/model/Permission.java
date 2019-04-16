package com.npde.example.model;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by lh on 2019-3-13.
 */
public class Permission extends BaseEntity {
    private String menuCode;
    private String menuName;
    private String permissionCode;
    private String permissionName;
    private int requiredPermission;
    private int permissionType;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public int getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(int requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }
}
