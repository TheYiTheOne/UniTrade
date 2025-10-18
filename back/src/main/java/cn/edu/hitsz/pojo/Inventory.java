package cn.edu.hitsz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Integer id; //ID
    private Integer productId; //货品ID
    private Integer warehouseId; //仓库ID
    private Integer quantity; //该货品在该仓库的数量
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
