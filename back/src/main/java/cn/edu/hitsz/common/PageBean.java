package cn.edu.hitsz.common;

import cn.edu.hitsz.pojo.Inventory;

import java.util.List;

/**
 * @Author Jayden
 * @Datetime Created in 15:40 2025/10/18
 * @Description
 */
public class PageBean<T> {
    private Long total;
    private List<T> rows;

    public List<T> getRows() {
        return rows;
    }

    public PageBean() {
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public PageBean(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
