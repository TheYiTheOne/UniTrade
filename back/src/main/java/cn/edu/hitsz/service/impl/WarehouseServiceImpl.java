package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.mapper.WarehouseMapper;
import cn.edu.hitsz.pojo.Warehouse;
import cn.edu.hitsz.pojo.PageBean;
import cn.edu.hitsz.service.WarehouseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;
    
    @Override
    public PageBean page(Integer page, Integer pageSize, String name) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<Warehouse> warehouseList = warehouseMapper.list(name);
        Page<Warehouse> p = (Page<Warehouse>) warehouseList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void delete(Integer id) {
        warehouseMapper.delete(id);
    }

    @Override
    public void add(Warehouse warehouse) {
        warehouse.setCreateTime(LocalDateTime.now());
        warehouse.setUpdateTime(LocalDateTime.now());
        warehouseMapper.insert(warehouse);
    }

    @Override
    public Warehouse getById(Integer id) {
        return warehouseMapper.getById(id);
    }

    @Override
    public void update(Warehouse warehouse) {
        warehouse.setUpdateTime(LocalDateTime.now());
        warehouseMapper.update(warehouse);
    }
}
