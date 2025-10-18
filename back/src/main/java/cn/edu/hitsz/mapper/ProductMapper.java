package cn.edu.hitsz.mapper;

import cn.edu.hitsz.pojo.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author pj
* @description 针对表【products(货品表，记录商品信息和价格)】的数据库操作Mapper
* @createDate 2025-10-17 22:34:02
* @Entity cn.edu.hitsz.pojo.Products
*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}




