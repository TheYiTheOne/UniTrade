package cn.edu.hitsz.controller;

import cn.edu.hitsz.common.Result;
import cn.edu.hitsz.pojo.*;
import cn.edu.hitsz.service.InventoryService;
import cn.edu.hitsz.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 库存管理Controller
 */
@Slf4j
@RequestMapping("/inventory")
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * 查询仓库数据
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name){
        //调用service分页查询
        PageBean pageBean = inventoryService.page(page,pageSize,name);
        return Result.success(pageBean);
    }
    /**
     * 进货入库
     */
    @PutMapping("/add")
    public Result addInventory(@RequestBody InventoryAddRequest request) {
        inventoryService.addInventory(request);
        return Result.success();
    }

    /**
     * 库存转移
     */
    @PutMapping("/transfer")
    public Result transferInventory(@RequestBody InventoryTransferRequest request) {
        inventoryService.transferInventory(request);
        return Result.success();
    }
}
