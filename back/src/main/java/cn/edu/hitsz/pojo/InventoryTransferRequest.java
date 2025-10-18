package cn.edu.hitsz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存转移请求实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransferRequest {
    private Integer productId; //货品ID
    private Integer startWarehouseId; //仓库ID
    private Integer endWarehouseId; //仓库ID
    private Integer quantity; //该货品在该仓库的数量
}
