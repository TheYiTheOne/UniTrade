package cn.edu.hitsz.controller;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.hitsz.common.LoginDTO;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     * POST /login
     */
    @PostMapping
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            Integer roleId = userService.login(loginDTO.getAccount(), loginDTO.getPassword());

            StpUtil.login(loginDTO.getAccount());
            StpUtil.getSession().set("roleId", roleId);

            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

            return Result.success(tokenInfo.getTokenValue());
        } catch (Exception e) {
            return Result.fail("登录失败：" + e.getMessage());
        }
    }
}