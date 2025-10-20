package cn.edu.hitsz.controller;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.hitsz.common.LoginDTO;
import cn.edu.hitsz.common.RegisterDTO;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.common.UserInfoDTO;
import cn.edu.hitsz.pojo.Role;
import cn.edu.hitsz.pojo.User;
import cn.edu.hitsz.service.OrderService;
import cn.edu.hitsz.service.RolePermissionService;
import cn.edu.hitsz.service.RoleService;
import cn.edu.hitsz.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private RoleService roleService;

    /**
     * 用户登录
     * POST /login
     */
    @PostMapping
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            Integer roleId = userService.login(loginDTO.getAccount(), loginDTO.getPassword());
            Integer permissionBitmask = rolePermissionService.getPermissionBitmask(roleId);

            StpUtil.login(loginDTO.getAccount());
            StpUtil.getSession().set("permissionBitmask", permissionBitmask);

            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

            return Result.success(tokenInfo.getTokenValue());
        } catch (Exception e) {
            return Result.fail("登录失败：" + e.getMessage());
        }
    }

    /**
     * 用户注册
     * POST /login/register
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterDTO registerDTO) {
        try {
            boolean success = userService.register(registerDTO);
            if (success) {
                return Result.success();
            } else {
                return Result.fail("注册失败");
            }
        } catch (Exception e) {
            return Result.fail("注册失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前用户信息和权限
     * GET /login/userinfo
     */
    @GetMapping("/userinfo")
    public Result<UserInfoDTO> getUserInfo() {
        try {
            // 获取当前登录用户账号
            String account = StpUtil.getLoginIdAsString();
            
            // 查询用户信息
            User user = userService.getUserByAccount(account);
            if (user == null) {
                return Result.fail("用户不存在");
            }
            
            // 查询角色信息
            Role role = roleService.getById(user.getRoleId());
            if (role == null) {
                return Result.fail("角色不存在");
            }
            
            // 获取用户权限
            List<String> permissions = rolePermissionService.getPermissionCodes(user.getRoleId());
            
            // 构建用户信息DTO
            UserInfoDTO userInfo = new UserInfoDTO();
            userInfo.setId(user.getId());
            userInfo.setAccount(user.getAccount());
            userInfo.setName(user.getName());
            userInfo.setRoleId(user.getRoleId());
            userInfo.setRoleName(role.getRoleName());
            userInfo.setPermissions(permissions);
            
            return Result.success(userInfo);
        } catch (Exception e) {
            return Result.fail("获取用户信息失败：" + e.getMessage());
        }
    }
}