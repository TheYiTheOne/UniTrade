package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.common.PageBean;
import cn.edu.hitsz.mapper.InventoryMapper;
import cn.edu.hitsz.pojo.InventoryAddRequest;
import cn.edu.hitsz.pojo.InventoryTransferRequest;
import cn.edu.hitsz.pojo.Inventory;
import cn.edu.hitsz.service.InventoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    
    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<Inventory> inventoryList = inventoryMapper.list(name);
        Page<Inventory> p = (Page<Inventory>) inventoryList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInventory(InventoryAddRequest request) {
        // 参数验证
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("入库数量必须大于0");
        }

        // 检查是否已存在该货品在该仓库的库存记录
        Inventory existingInventory = inventoryMapper.selectByProductAndWarehouse(
                request.getProductId(), request.getWarehouseId());

        LocalDateTime now = LocalDateTime.now();

        if (existingInventory != null) {
            // 更新现有库存
            Integer newQuantity = existingInventory.getQuantity() + request.getQuantity();
            inventoryMapper.update(existingInventory.getId(), newQuantity, now);
        } else {
            // 创建新的库存记录
            Inventory newInventory = new Inventory();
            newInventory.setProductId(request.getProductId());
            newInventory.setWarehouseId(request.getWarehouseId());
            newInventory.setQuantity(request.getQuantity());
            newInventory.setCreateTime(now);
            newInventory.setUpdateTime(now);

            inventoryMapper.insert(newInventory);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferInventory(InventoryTransferRequest request) {
        // 参数验证
        if (request.getStartWarehouseId().equals(request.getEndWarehouseId())) {
            throw new IllegalArgumentException("起始仓库和目标仓库不能相同");
        }
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("转移数量必须大于0");
        }

        // 检查起始仓库是否有足够库存
        Inventory startInventory = inventoryMapper.selectByProductAndWarehouse(
                request.getProductId(), request.getStartWarehouseId());

        if (startInventory == null || startInventory.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("起始仓库库存不足");
        }

        LocalDateTime now = LocalDateTime.now();

        // 减少起始仓库库存
        Integer newStartQuantity = startInventory.getQuantity() - request.getQuantity();
        if (newStartQuantity != 0) {
            inventoryMapper.update(startInventory.getId(), newStartQuantity, now);
        } else {
            inventoryMapper.delete(startInventory.getId());
        }

        // 增加目标仓库库存
        Inventory endInventory = inventoryMapper.selectByProductAndWarehouse(
                request.getProductId(), request.getEndWarehouseId());

        if (endInventory != null) {
            // 更新现有库存
            Integer newEndQuantity = endInventory.getQuantity() + request.getQuantity();
            inventoryMapper.update(endInventory.getId(), newEndQuantity, now);
        } else {
            // 创建新的库存记录
            Inventory newInventory = new Inventory();
            newInventory.setProductId(request.getProductId());
            newInventory.setWarehouseId(request.getEndWarehouseId());
            newInventory.setQuantity(request.getQuantity());
            newInventory.setCreateTime(now);
            newInventory.setUpdateTime(now);

            inventoryMapper.insert(newInventory);
        }
    }
}
