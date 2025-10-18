package cn.edu.hitsz.controller;

import cn.edu.hitsz.anno.Log;
import cn.edu.hitsz.pojo.*;
import cn.edu.hitsz.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 仓库管理Controller
 */
@Slf4j
@RequestMapping("/warehouses")
@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 查询仓库数据
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name){
        //调用service分页查询
        PageBean pageBean = warehouseService.page(page,pageSize,name);
        return Result.success(pageBean);
    }


    /**
     * 删除仓库
     * @return
     */
    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) throws Exception {
        //调用service删除仓库
        warehouseService.delete(id);
        return Result.success();
    }


    /**
     * 新增仓库
     * @return
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Warehouse warehouse){
        //调用service新增仓库
        warehouseService.add(warehouse);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Warehouse warehouse = warehouseService.getById(id);
        return Result.success(warehouse);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Warehouse warehouse){
        warehouseService.update(warehouse);
        return Result.success();
    }
}
