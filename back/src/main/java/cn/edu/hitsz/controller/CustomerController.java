package cn.edu.hitsz.controller;

import cn.edu.hitsz.common.PageBean;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.pojo.Customer;
import cn.edu.hitsz.service.CustomerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 客户管理控制器
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 客户列表查询
     * @param name 客户姓名（模糊查询，可为空）
     * @param page 分页查询的页码，如果未指定，默认为1
     * @param pageSize 分页查询的每页记录数，如果未指定，默认为10
     * @return 分页查询结果
     */
    @GetMapping
    public Result<PageBean<Customer>> list(@RequestParam(required = false) String name,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            // 创建分页对象
            Page<Customer> pageInfo = new Page<>(page, pageSize);
            
            // 构建查询条件
            LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
            if (name != null && !name.trim().isEmpty()) {
                queryWrapper.like(Customer::getName, name);
            }
            queryWrapper.orderByDesc(Customer::getCreateTime);
            
            // 执行分页查询
            Page<Customer> result = customerService.page(pageInfo, queryWrapper);
            
            // 构建返回结果
            PageBean<Customer> pageBean = new PageBean<>();
            pageBean.setTotal(result.getTotal());
            pageBean.setRows(result.getRecords());
            
            return Result.success(pageBean);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询客户
     * @param id 客户ID
     * @return 客户信息
     */
    @GetMapping("/{id}")
    public Result<Customer> getById(@PathVariable Integer id) {
        try {
            Customer customer = customerService.getById(id);
            if (customer == null) {
                return Result.fail("客户不存在");
            }
            return Result.success(customer);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }

    /**
     * 添加客户
     * @param customer 客户信息
     * @return 操作结果
     */
    @PostMapping
    public Result<Void> add(@RequestBody Customer customer) {
        try {
            // 设置创建时间和更新时间
            Date now = new Date();
            customer.setCreateTime(now);
            customer.setUpdateTime(now);
            
            boolean success = customerService.save(customer);
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
     * 修改客户
     * @param customer 客户信息
     * @return 操作结果
     */
    @PutMapping
    public Result<Void> update(@RequestBody Customer customer) {
        try {
            // 设置更新时间
            customer.setUpdateTime(new Date());
            
            boolean success = customerService.updateById(customer);
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
     * 删除客户
     * @param id 客户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        try {
            boolean success = customerService.removeById(id);
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
