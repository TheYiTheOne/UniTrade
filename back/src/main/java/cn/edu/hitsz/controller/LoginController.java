package cn.edu.hitsz.controller;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.hitsz.common.LoginDTO;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.service.OrderService;
import cn.edu.hitsz.service.RolePermissionService;
import cn.edu.hitsz.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private RolePermissionService rolePermissionService;

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
}