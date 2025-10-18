package cn.edu.hitsz.controller;

import cn.edu.hitsz.common.PageBean;
import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.pojo.Order;
import cn.edu.hitsz.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单管理 API 控制器
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    // ==================== 查询接口 ====================

    /**
     * 分页查询订单列表，支持按货品名模糊搜索
     * GET /api/orders?page=1&pageSize=10&name=鼠标
     */
    @GetMapping
    public Result<PageBean> listOrders(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        // 参数校验与修正
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;

        try {
            IPage<Order> orderPage = orderService.listOrders(name, page, pageSize);

            PageBean<Order> pageBean = new PageBean<>();
            pageBean.setTotal(orderPage.getTotal());
            pageBean.setRows(orderPage.getRecords()); // MyBatis Plus 默认字段是 records


            return Result.success(pageBean);
        } catch (Exception e) {
            return Result.fail("分页查询失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取订单详情
     * GET /api/orders/1
     */
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Integer id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                return Result.fail("订单不存在");
            }
            return Result.success(order);
        } catch (Exception e) {
            return Result.fail("查询失败：" + e.getMessage());
        }
    }

    // ==================== 增删改接口 ====================

    /**
     * 创建新订单
     * POST /api/orders
     * Body: JSON
     */
    @PostMapping
    public Result<Void> addOrder(@RequestBody Order order) {
        try {
            orderService.addOrder(order);
            return Result.success();
        } catch (Exception e) {
            return Result.fail("订单创建失败：" + e.getMessage());
        }
    }

    /**
     * 更新订单（仅草稿状态可修改）
     * PUT /api/orders
     */
    @PutMapping
    public Result<Void> updateOrder(@RequestBody Order order) {
        try {
            orderService.updateOrder(order);
            return Result.success();
        } catch (Exception e) {
            return Result.fail("订单更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除订单（仅草稿状态可删除）
     * DELETE /api/orders/1
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteOrder(@PathVariable Integer id) {
        try {
            orderService.deleteOrderById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.fail("订单删除失败：" + e.getMessage());
        }
    }

    // ==================== 状态流转接口 ====================

    /**
     * 提交订单：草稿 → 已审核
     * PUT /api/orders/submit/1
     */
    @PutMapping("/submit/{id}")
    public Result<Void> submitOrder(@PathVariable Integer id) {
        try {
            orderService.submitOrder(id);
            return Result.success();
        } catch (Exception e) {
            return Result.fail("提交失败：" + e.getMessage());
        }
    }

    /**
     * 收款：已审核 → 已收款
     * PUT /api/orders/pay/1
     */
    @PutMapping("/pay/{id}")
    public Result<Void> payOrder(@PathVariable Integer id) {
        try {
            orderService.payOrder(id);
            return Result.success();
        } catch (Exception e) {
            return Result.fail("收款失败：" + e.getMessage());
        }
    }

    /**
     * 退货：已收款 → 已退货
     * PUT /api/orders/cancel/1
     */
    @PutMapping("/cancel/{id}")
    public Result<Void> cancelOrder(@PathVariable Integer id) {
        try {
            orderService.cancelOrder(id);
            return Result.success();
        } catch (Exception e) {
            return Result.fail("退货失败：" + e.getMessage());
        }
    }
}