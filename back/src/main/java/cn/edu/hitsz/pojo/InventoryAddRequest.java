package cn.edu.hitsz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进货入库请求实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAddRequest {
    private Integer productId; //货品ID
    private Integer warehouseId; //仓库ID
    private Integer quantity; //该货品在该仓库增加的数量
}
