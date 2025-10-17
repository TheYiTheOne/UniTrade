package cn.edu.hitsz.controller;

import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.pojo.Order;
import cn.edu.hitsz.service.OrdersService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理 API 控制器
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    // ==================== 查询接口 ====================

    /**
     * 分页查询订单列表，支持按货品名模糊搜索
     * GET /api/orders?page=1&pageSize=10&name=鼠标
     */
    @GetMapping
    public Result<IPage<Order>> listOrders(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        // 参数校验与修正
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;

        try {
            IPage<Order> orderPage = ordersService.listOrders(name, page, pageSize);
            return Result.success(orderPage);
        } catch (Exception e) {
            return Result.fail(500, "分页查询失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取订单详情
     * GET /api/orders/1
     */
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Integer id) {
        try {
            Order order = ordersService.getOrderById(id);
            if (order == null) {
                return Result.fail(404, "订单不存在");
            }
            return Result.success(order);
        } catch (IllegalArgumentException e) {
            return Result.fail(400, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "查询失败：" + e.getMessage());
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
            ordersService.addOrder(order);
            return Result.success("订单创建成功");
        } catch (IllegalArgumentException e) {
            return Result.fail(400, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "订单创建失败：" + e.getMessage());
        }
    }

    /**
     * 更新订单（仅草稿状态可修改）
     * PUT /api/orders
     */
    @PutMapping
    public Result<Void> updateOrder(@RequestBody Order order) {
        try {
            ordersService.updateOrder(order);
            return Result.success("订单更新成功");
        } catch (IllegalArgumentException e) {
            return Result.fail(400, e.getMessage());
        } catch (IllegalStateException e) {
            return Result.fail(403, e.getMessage()); // 权限不足或状态不允许
        } catch (Exception e) {
            return Result.fail(500, "订单更新失败：" + e.getMessage());
        }
    }

    /**
     * 删除订单（仅草稿状态可删除）
     * DELETE /api/orders/1
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteOrder(@PathVariable Integer id) {
        try {
            ordersService.deleteOrderById(id);
            return Result.success("订单删除成功");
        } catch (IllegalArgumentException e) {
            return Result.fail(400, e.getMessage());
        } catch (IllegalStateException e) {
            return Result.fail(403, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "订单删除失败：" + e.getMessage());
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
            ordersService.submitOrder(id);
            return Result.success("订单已提交（已审核）");
        } catch (IllegalArgumentException e) {
            return Result.fail(400, e.getMessage());
        } catch (IllegalStateException e) {
            return Result.fail(403, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "提交失败：" + e.getMessage());
        }
    }

    /**
     * 收款：已审核 → 已收款
     * PUT /api/orders/pay/1
     */
    @PutMapping("/pay/{id}")
    public Result<Void> payOrder(@PathVariable Integer id) {
        try {
            ordersService.payOrder(id);
            return Result.success("收款成功");
        } catch (IllegalArgumentException e) {
            return Result.fail(400, e.getMessage());
        } catch (IllegalStateException e) {
            return Result.fail(403, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "收款失败：" + e.getMessage());
        }
    }

    /**
     * 退货：已收款 → 已退货
     * PUT /api/orders/cancel/1
     */
    @PutMapping("/cancel/{id}")
    public Result<Void> cancelOrder(@PathVariable Integer id) {
        try {
            ordersService.cancelOrder(id);
            return Result.success("订单已退货");
        } catch (IllegalArgumentException e) {
            return Result.fail(400, e.getMessage());
        } catch (IllegalStateException e) {
            return Result.fail(403, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "退货失败：" + e.getMessage());
        }
    }
}