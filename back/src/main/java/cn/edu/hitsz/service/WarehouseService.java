package cn.edu.hitsz.service;

import cn.edu.hitsz.pojo.Warehouse;
import cn.edu.hitsz.pojo.PageBean;

public interface WarehouseService {
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean page(Integer page, Integer pageSize,String name);

    /**
     * 根据ID删除仓库
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增仓库
     * @param warehouse
     */
    void add(Warehouse warehouse);

    /**
     * 根据ID查询仓库
     * @param id
     * @return
     */
    Warehouse getById(Integer id);

    /**
     * 修改仓库信息
     * @param warehouse
     */
    void update(Warehouse warehouse);

}
