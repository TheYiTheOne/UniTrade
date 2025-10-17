package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Product;
import cn.edu.hitsz.service.ProductService;
import cn.edu.hitsz.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【products(货品表，记录商品信息和价格)】的数据库操作Service实现
* @createDate 2025-10-17 23:55:08
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

}




