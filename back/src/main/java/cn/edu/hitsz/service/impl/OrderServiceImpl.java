package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.mapper.CustomerMapper;
import cn.edu.hitsz.mapper.ProductMapper;
import cn.edu.hitsz.mapper.WarehouseMapper;
import cn.edu.hitsz.pojo.Order;

import cn.edu.hitsz.pojo.Product;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.service.OrderService;
import cn.edu.hitsz.mapper.OrderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private WarehouseMapper warehouseMapper;

    // 状态常量
    private static final int STATUS_DRAFT = 0;        // 草稿
    private static final int STATUS_APPROVED = 1;     // 已审核
    private static final int STATUS_PAID = 2;         // 已收款
    private static final int STATUS_RETURNED = 3;     // 已退货

    // 类型常量
    private static final int TYPE_WHOLESALE = 0;      // 批发
    private static final int TYPE_RETAIL = 1;         // 零售


    @Override
    public IPage<Order> listOrders(String name, Integer page, Integer pageSize) {
        // 创建 MyBatis-Plus 分页对象
        Page<Order> orderPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.trim().isEmpty()) {
            wrapper.like(Order::getProductName, name.trim());
        }
        wrapper.orderByDesc(Order::getId);      // 按ID降序

        // 调用 Mapper 自定义分页查询
        return baseMapper.selectPage(orderPage, wrapper);
    }

    @Override
    public Order getOrderById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("订单数据不能为空");
        }

        validateOrder(order);

        // 计算总价
        Product product = productMapper.selectById(order.getProductId());
        BigDecimal unitPrice = product.getRetailPrice();
        order.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(order.getQuantity())));

        // 设置客户名、货品名和仓库名
        order.setCustomerName(customerMapper.selectById(order.getCustomerId()).getName());
        order.setProductName(productMapper.selectById(order.getProductId()).getName());
        order.setWarehouseName(warehouseMapper.getById(order.getWarehouseId()).getName());

        // 设置状态和时间
        order.setStatus(STATUS_DRAFT);
        Date now = new Date();
        order.setCreateTime(now);
        order.setUpdateTime(now);

        baseMapper.insert(order);
    }

    @Override
    @Transactional
    public void updateOrder(Order order) {
        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException("订单ID不能为空");
        }

        Order existing = baseMapper.selectById(order.getId());
        if (existing == null) {
            throw new RuntimeException("订单不存在");
        }

        if (existing.getStatus() != STATUS_DRAFT) {
            throw new IllegalStateException("只能修改草稿状态的订单");
        }

        // 更新字段
        existing.setCustomerId(order.getCustomerId());
        existing.setCustomerName(customerMapper.selectById(order.getCustomerId()).getName());
        existing.setProductId(order.getProductId());
        existing.setProductName(productMapper.selectById(order.getProductId()).getName());
        existing.setWarehouseId(order.getWarehouseId());
        existing.setWarehouseName(warehouseMapper.getById(order.getWarehouseId()).getName());
        existing.setQuantity(order.getQuantity());
        existing.setType(order.getType());

        // 重新计算总价
        Product product = productMapper.selectById(order.getProductId());
        BigDecimal unitPrice = product.getRetailPrice();
        existing.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(order.getQuantity())));

        existing.setUpdateTime(new Date());

        baseMapper.updateById(existing);
    }

    @Override
    @Transactional
    public void deleteOrderById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("订单ID必须大于0");
        }

        Order order = baseMapper.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (order.getStatus() != STATUS_DRAFT) {
            throw new IllegalStateException("只能删除草稿状态的订单");
        }

        baseMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void submitOrder(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("订单ID必须大于0");
        }

        Order order = baseMapper.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (order.getStatus() != STATUS_DRAFT) {
            throw new IllegalStateException("只能审核草稿状态的订单");
        }

        order.setStatus(STATUS_APPROVED);
        order.setUpdateTime(new Date());

        baseMapper.updateById(order);
    }

    @Override
    @Transactional
    public void payOrder(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("订单ID必须大于0");
        }

        Order order = baseMapper.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (order.getStatus() != STATUS_APPROVED) {
            throw new IllegalStateException("只能对已审核的订单进行收款");
        }

        order.setStatus(STATUS_PAID);
        order.setUpdateTime(new Date());

        baseMapper.updateById(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("订单ID必须大于0");
        }

        Order order = baseMapper.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        if (order.getStatus() != STATUS_PAID) {
            throw new IllegalStateException("只能对已收款的订单进行退货");
        }

        order.setStatus(STATUS_RETURNED);
        order.setUpdateTime(new Date());

        baseMapper.updateById(order);
    }

    /**
     * 校验订单必要字段
     */
    private void validateOrder(Order order) {
        if (order.getCustomerId() == null ||
                order.getProductId() == null ||
                order.getWarehouseId() == null ||
                order.getQuantity() == null ||
                order.getType() == null) {
            throw new IllegalArgumentException("客户、货品、仓库、数量、类型不能为空");
        }
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("货品数量必须大于0");
        }
        if (order.getType() != TYPE_WHOLESALE && order.getType() != TYPE_RETAIL) {
            throw new IllegalArgumentException("销售单种类必须为 0（批发）或 1（零售）");
        }
    }
}




