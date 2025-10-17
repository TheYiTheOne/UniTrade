package cn.edu.hitsz.mapper;

import cn.edu.hitsz.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【orders(销售单表，记录销售订单详情)】的数据库操作Mapper
* @createDate 2025-10-17 22:59:11
* @Entity cn.edu.hitsz.pojo.Orders
*/
public interface OrdersMapper extends BaseMapper<Order> {

    /**
     * 分页查询销售单列表（支持货品名模糊查询）
     * @param page MyBatis-Plus所提供的分页对象，必须位于第一个参数的位置
     * @param name
     * @return
     */
    IPage<Order> selectPageVo(@Param("page") Page<Order> page, @Param("name") String name);

}




