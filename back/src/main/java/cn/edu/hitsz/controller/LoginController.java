package cn.edu.hitsz.controller;


import cn.edu.hitsz.common.LoginDTO;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
            String token = userService.login(loginDTO.getAccount(), loginDTO.getPassword());
            return Result.success(token); // code=1, msg="success"
        } catch (IllegalArgumentException e) {
            return Result.fail(0, e.getMessage());
        } catch (Exception e) {
            return Result.fail(0, "系统异常");
        }
    }
}