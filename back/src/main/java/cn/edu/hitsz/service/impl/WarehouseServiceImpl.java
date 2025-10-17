package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Warehouse;
import cn.edu.hitsz.service.WarehouseService;
import cn.edu.hitsz.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【warehouses(仓库表，记录仓库信息)】的数据库操作Service实现
* @createDate 2025-10-18 00:20:11
*/
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse>
    implements WarehouseService{

}




