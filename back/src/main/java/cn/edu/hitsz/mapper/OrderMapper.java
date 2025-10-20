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
public interface OrderMapper extends BaseMapper<Order> {
}




