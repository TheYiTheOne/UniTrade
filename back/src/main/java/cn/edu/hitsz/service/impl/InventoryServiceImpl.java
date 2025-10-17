package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Inventory;
import cn.edu.hitsz.service.InventoryService;
import cn.edu.hitsz.mapper.InventoryMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【inventory(库存表，记录每个仓库中每种货品的库存情况)】的数据库操作Service实现
* @createDate 2025-10-18 00:20:49
*/
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory>
    implements InventoryService{

}




