package cn.edu.hitsz.mapper;

import cn.edu.hitsz.pojo.Warehouse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WarehouseMapper {
    /**
     * 仓库信息分页查询
     * @param name
     * @return
     */
    public List<Warehouse> list(String name);

    /**
     * 根据id删除仓库
     * @param id
     */
    @Delete("delete from warehouses where id = #{id}")
    void delete(Integer id);

    /**
     * 新增仓库
     * @param warehouse
     */
    @Insert("insert into warehouses(name, phone, address, create_time, update_time) " +
            " values(#{name},#{phone},#{address},#{createTime},#{updateTime})")
    void insert(Warehouse warehouse);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    @Select("select * from warehouses where  id = #{id}")
    Warehouse getById(Integer id);

    /**
     * 更新员工
     * @param warehouse
     */
    void update(Warehouse warehouse);
}
