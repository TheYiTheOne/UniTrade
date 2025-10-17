package cn.edu.hitsz.unitrade.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer customerId;

    /**
     * 
     */
    private Integer productId;

    /**
     * 
     */
    private Integer warehouseId;

    /**
     * 
     */
    private Integer quantity;

    /**
     * 
     */
    private Object type;

    /**
     * 
     */
    private BigDecimal totalPrice;

    /**
     * 
     */
    private Object status;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}