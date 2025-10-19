package cn.edu.hitsz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售单表，记录销售订单详情
 *
 * @TableName orders
 */
@TableName(value = "orders")
@Data
public class Order {
    /**
     * 主键，自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 客户ID（关联customers表）
     */
    private Integer customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 商品ID（关联products表）
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 仓库ID（关联warehouses表）
     */
    private Integer warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 订单数量
     */
    private Integer quantity;

    /**
     * 订单类型（如0=批发订单，1=零售订单）
     */
    private Integer type;

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 订单状态（如0=草稿，1=已审核，2=已收款，3=已退货）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;
}