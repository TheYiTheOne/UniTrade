package cn.edu.hitsz.mapper;

import cn.edu.hitsz.pojo.*;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface InventoryMapper {
    /**
     * 员工信息分页查询
     * @param name
     * @return
     */
    List<Inventory> list(String name);

    /**
     * 根据货品和仓库ID查询库存记录
     * @param productId
     * @param warehouseId
     * @return
     */
    @Select("select * from inventory where product_id = #{productId} and warehouse_id = #{warehouseId}")
    Inventory selectByProductAndWarehouse(Integer productId, Integer warehouseId);

    /**
     * 根据ID删除库存记录
     * @param id
     * @return
     */
    @Delete("delete from inventory where id = #{id}")
    void delete(Integer id);

    /**
     * 新增库存记录
     * @param inventory
     */
    @Insert("insert into inventory(product_id, product_name, warehouse_id, warehouse_name, quantity, create_time, update_time) " +
            " values(#{productId},#{productName},#{warehouseId},#{warehouseName},#{quantity},#{createTime},#{updateTime})")
    void insert(Inventory inventory);

    /**
     * 更新库存记录
     * @param
     */
    @Update("update inventory set quantity = #{quantity}, update_time = #{updateTime} where id = #{id}")
    void update(Integer id, Integer quantity, LocalDateTime updateTime);
}
