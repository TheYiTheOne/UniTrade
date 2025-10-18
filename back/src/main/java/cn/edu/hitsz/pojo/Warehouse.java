package cn.edu.hitsz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 仓库实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    private Integer id; //ID
    private String name; //仓库名称
    private String phone; //仓库联系电话
    private String address; //仓库地址
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
