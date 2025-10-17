package cn.edu.hitsz.service;


import cn.edu.hitsz.pojo.Order;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【orders(销售单表，记录销售订单详情)】的数据库操作Service
* @createDate 2025-10-17 22:59:11
*/
public interface OrdersService extends IService<Order> {

    /**
     * 分页查询销售单列表（支持货品名模糊查询）
     */
    IPage<Order> listOrders(String name, Integer page, Integer pageSize);

    /**
     * 根据ID查询销售单详情
     */
    Order getOrderById(Integer id);

    /**
     * 添加销售单（初始状态为草稿）
     */
    void addOrder(Order order);

    /**
     * 修改销售单（仅限草稿状态）
     */
    void updateOrder(Order order);

    /**
     * 删除销售单（仅限草稿状态）
     */
    void deleteOrderById(Integer id);

    /**
     * 审核销售单（草稿 -> 已审核）
     */
    void submitOrder(Integer id);

    /**
     * 收款操作（已审核 -> 已收款）
     */
    void payOrder(Integer id);

    /**
     * 退货操作（已收款 -> 已退货）
     */
    void cancelOrder(Integer id);

}
