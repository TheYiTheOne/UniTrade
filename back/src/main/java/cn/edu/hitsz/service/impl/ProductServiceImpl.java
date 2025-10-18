package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Product;
import cn.edu.hitsz.service.ProductService;
import cn.edu.hitsz.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
* @author pj
* @description 针对表【products(货品表，记录商品信息和价格)】的数据库操作Service实现
* @createDate 2025-10-17 22:34:02
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService {

    /**
     * 根据货品名称检查是否存在
     * @param name 货品名称
     * @return 是否存在
     */
    public boolean existsByName(String name) {
        return this.lambdaQuery()
                .eq(Product::getName, name)
                .count() > 0;
    }

    /**
     * 根据货品名称检查是否存在（排除指定ID）
     * @param name 货品名称
     * @param id 要排除的ID
     * @return 是否存在
     */
    public boolean existsByNameAndIdNot(String name, Integer id) {
        return this.lambdaQuery()
                .eq(Product::getName, name)
                .ne(Product::getId, id)
                .count() > 0;
    }
}




