package cn.edu.hitsz.common;

/**
 * 用户注册DTO
 */
public class RegisterDTO {
    private String account;
    private String password;
    private String name;
    private Integer roleId;

    public RegisterDTO() {
    }

    public RegisterDTO(String account, String password, String name, Integer roleId) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.roleId = roleId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
