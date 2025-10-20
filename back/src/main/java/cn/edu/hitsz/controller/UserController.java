package cn.edu.hitsz.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.edu.hitsz.common.PageBean;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.pojo.User;
import cn.edu.hitsz.service.RolePermissionService;
import cn.edu.hitsz.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 用户列表查询
     * @param name 用户名（模糊查询，可为空）
     * @param account 账号（模糊查询，可为空）
     * @param page 分页查询的页码，如果未指定，默认为1
     * @param pageSize 分页查询的每页记录数，如果未指定，默认为10
     * @return 分页查询结果
     */
    @GetMapping
    public Result<PageBean<User>> list(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String account,
                                       @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            // 权限校验：查询用户表
            String currentAccount = StpUtil.getLoginIdAsString();
            User currentUser = userService.getUserByAccount(currentAccount);
            if (!rolePermissionService.hasPermissionByCode(currentUser.getRoleId(), "VIEW_USERS")) {
                return Result.fail("权限不足，无法查询用户信息");
            }
            // 创建分页对象
            Page<User> pageInfo = new Page<>(page, pageSize);
            
            // 构建查询条件
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            if (name != null && !name.trim().isEmpty()) {
                queryWrapper.like(User::getName, name);
            }
            if (account != null && !account.trim().isEmpty()) {
                queryWrapper.like(User::getAccount, account);
            }
            queryWrapper.orderByDesc(User::getCreateTime);
            
            // 执行分页查询
            Page<User> result = userService.page(pageInfo, queryWrapper);
            
            // 隐藏密码字段
            result.getRecords().forEach(user -> user.setPassword("******"));
            
            // 构建返回结果
            PageBean<User> pageBean = new PageBean<>();
            pageBean.setTotal(result.getTotal());
            pageBean.setRows(result.getRecords());
            
            return Result.success(pageBean);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Integer id) {
        try {
            // 权限校验：查询用户表
            String currentAccount = StpUtil.getLoginIdAsString();
            User currentUser = userService.getUserByAccount(currentAccount);
            if (!rolePermissionService.hasPermissionByCode(currentUser.getRoleId(), "VIEW_USERS")) {
                return Result.fail("权限不足，无法查询用户信息");
            }
            
            User user = userService.getById(id);
            if (user == null) {
                return Result.fail("用户不存在");
            }
            // 隐藏密码
            user.setPassword("******");
            return Result.success(user);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return 操作结果
     */
    @PostMapping
    public Result<Void> add(@RequestBody User user) {
        try {
            // 权限校验：编辑用户信息
            String currentAccount = StpUtil.getLoginIdAsString();
            User currentUser = userService.getUserByAccount(currentAccount);
            if (!rolePermissionService.hasPermissionByCode(currentUser.getRoleId(), "EDIT_USERS")) {
                return Result.fail("权限不足，无法添加用户");
            }
            
            // 检查账号是否已存在
            User existingUser = userService.getUserByAccount(user.getAccount());
            if (existingUser != null) {
                return Result.fail("账号已存在");
            }
            
            // 设置创建时间和更新时间
            Date now = new Date();
            user.setCreateTime(now);
            user.setUpdateTime(now);
            
            boolean success = userService.save(user);
            if (success) {
                return Result.success();
            } else {
                return Result.fail("添加失败");
            }
        } catch (Exception e) {
            return Result.fail("添加失败：" + e.getMessage());
        }
    }

    /**
     * 修改用户
     * @param user 用户信息
     * @return 操作结果
     */
    @PutMapping
    public Result<Void> update(@RequestBody User user) {
        try {
            // 权限校验：编辑用户信息
            String currentAccount = StpUtil.getLoginIdAsString();
            User currentUser = userService.getUserByAccount(currentAccount);
            if (!rolePermissionService.hasPermissionByCode(currentUser.getRoleId(), "EDIT_USERS")) {
                return Result.fail("权限不足，无法修改用户信息");
            }
            
            // 如果密码是******，则不更新密码
            if ("******".equals(user.getPassword())) {
                user.setPassword(null);
            }
            
            // 设置更新时间
            user.setUpdateTime(new Date());
            
            boolean success = userService.updateById(user);
            if (success) {
                return Result.success();
            } else {
                return Result.fail("修改失败");
            }
        } catch (Exception e) {
            return Result.fail("修改失败：" + e.getMessage());
        }
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        try {
            // 权限校验：编辑用户信息
            String currentAccount = StpUtil.getLoginIdAsString();
            User currentUser = userService.getUserByAccount(currentAccount);
            if (!rolePermissionService.hasPermissionByCode(currentUser.getRoleId(), "EDIT_USERS")) {
                return Result.fail("权限不足，无法删除用户");
            }
            
            boolean success = userService.removeById(id);
            if (success) {
                return Result.success();
            } else {
                return Result.fail("删除失败");
            }
        } catch (Exception e) {
            return Result.fail("删除失败：" + e.getMessage());
        }
    }
}
