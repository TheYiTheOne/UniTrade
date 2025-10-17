package cn.edu.hitsz.unitrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.unitrade.model.entity.Orders;
import cn.edu.hitsz.unitrade.service.OrdersService;
import cn.edu.hitsz.unitrade.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author pj
* @description 针对表【orders】的数据库操作Service实现
* @createDate 2025-10-17 17:23:35
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




