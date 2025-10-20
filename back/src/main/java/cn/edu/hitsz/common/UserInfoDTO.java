package cn.edu.hitsz.common;

import java.util.List;

/**
 * 用户信息DTO
 */
public class UserInfoDTO {
    private Integer id;
    private String account;
    private String name;
    private Integer roleId;
    private String roleName;
    private List<String> permissions;

    public UserInfoDTO() {
    }

    public UserInfoDTO(Integer id, String account, String name, Integer roleId, String roleName, List<String> permissions) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.roleId = roleId;
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
