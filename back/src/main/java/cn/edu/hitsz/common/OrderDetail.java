package cn.edu.hitsz.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Jayden
 * @Datetime Created in 16:58 2025/10/19
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Integer orderId;
    private String customerName;
    private String productName;
    private String warehouseName;
    private Integer quantity;
    private String type;
    private BigDecimal totalPrice;
    private String status;
}
