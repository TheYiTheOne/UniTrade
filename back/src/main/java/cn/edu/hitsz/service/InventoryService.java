package cn.edu.hitsz.service;

import cn.edu.hitsz.pojo.InventoryAddRequest;
import cn.edu.hitsz.pojo.InventoryTransferRequest;
import cn.edu.hitsz.pojo.PageBean;

public interface InventoryService {

    /**
     * 分页查询
     */
    PageBean page(Integer page, Integer pageSize, String name);

    /**
     * 进货入库
     */
    void addInventory(InventoryAddRequest request);

    /**
     * 库存转移
     */
    void transferInventory(InventoryTransferRequest request);

}
