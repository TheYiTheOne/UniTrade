package cn.edu.hitsz.controller;

import cn.edu.hitsz.common.PageBean;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.pojo.Product;
import cn.edu.hitsz.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 货品管理控制器
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 货品列表查询
     * @param name 货品名（模糊查询，可为空）
     * @param page 分页查询的页码，如果未指定，默认为1
     * @param pageSize 分页查询的每页记录数，如果未指定，默认为10
     * @return 分页查询结果
     */
    @GetMapping
    public Result<PageBean<Product>> list(@RequestParam(required = false) String name,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            // 创建分页对象
            Page<Product> pageInfo = new Page<>(page, pageSize);
            
            // 构建查询条件
            LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
            if (name != null && !name.trim().isEmpty()) {
                queryWrapper.like(Product::getName, name);
            }
            queryWrapper.orderByDesc(Product::getCreateTime);
            
            // 执行分页查询
            Page<Product> result = productService.page(pageInfo, queryWrapper);
            
            // 构建返回结果
            PageBean<Product> pageBean = new PageBean<>();
            pageBean.setTotal(result.getTotal());
            pageBean.setRows(result.getRecords());
            
            return Result.success(pageBean);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询货品
     * @param id 货品ID
     * @return 货品信息
     */
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Integer id) {
        try {
            Product product = productService.getById(id);
            if (product == null) {
                return Result.fail("货品不存在");
            }
            return Result.success(product);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }

    /**
     * 添加货品
     * @param product 货品信息
     * @return 操作结果
     */
    @PostMapping
    public Result<Void> add(@RequestBody Product product) {
        try {
            // 设置创建时间和更新时间
            Date now = new Date();
            product.setCreateTime(now);
            product.setUpdateTime(now);
            
            boolean success = productService.save(product);
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
     * 修改货品
     * @param product 货品信息
     * @return 操作结果
     */
    @PutMapping
    public Result<Void> update(@RequestBody Product product) {
        try {
            // 设置更新时间
            product.setUpdateTime(new Date());
            
            boolean success = productService.updateById(product);
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
     * 删除货品
     * @param id 货品ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        try {
            boolean success = productService.removeById(id);
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
