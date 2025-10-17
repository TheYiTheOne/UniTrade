package cn.edu.hitsz.unitrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.unitrade.model.entity.Inventory;
import cn.edu.hitsz.unitrade.service.InventoryService;
import cn.edu.hitsz.unitrade.mapper.InventoryMapper;
import org.springframework.stereotype.Service;

/**
* @author pj
* @description 针对表【inventory】的数据库操作Service实现
* @createDate 2025-10-17 17:23:35
*/
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory>
    implements InventoryService{

}




