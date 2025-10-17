package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.mapper.ProductMapper;
import cn.edu.hitsz.pojo.Order;

import cn.edu.hitsz.pojo.Product;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.service.OrdersService;
import cn.edu.hitsz.mapper.OrdersMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;



/**
* @author Administrator
* @description 针对表【orders(销售单表，记录销售订单详情)】的数据库操作Service实现
* @createDate 2025-10-17 22:59:11
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Order>
    implements OrdersService{

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private ProductMapper productMapper;

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

        // 调用 Mapper 自定义分页查询
        return baseMapper.selectPageVo(orderPage, name);
    }

    @Override
    public Order getOrderById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("订单ID必须大于0");
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
        existing.setProductId(order.getProductId());
        existing.setWarehouseId(order.getWarehouseId());
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




