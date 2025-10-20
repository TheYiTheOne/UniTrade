package cn.edu.hitsz.controller;

import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.pojo.Role;
import cn.edu.hitsz.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色列表
     * @return 角色列表
     */
    @GetMapping
    public Result<List<Role>> list() {
        try {
            List<Role> roles = roleService.list();
            return Result.success(roles);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }
}
