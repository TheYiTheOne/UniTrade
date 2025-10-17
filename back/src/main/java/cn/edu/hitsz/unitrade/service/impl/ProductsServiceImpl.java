package cn.edu.hitsz.unitrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.unitrade.model.entity.Products;
import cn.edu.hitsz.unitrade.service.ProductsService;
import cn.edu.hitsz.unitrade.mapper.ProductsMapper;
import org.springframework.stereotype.Service;

/**
* @author pj
* @description 针对表【products】的数据库操作Service实现
* @createDate 2025-10-17 17:23:35
*/
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products>
    implements ProductsService{

}




