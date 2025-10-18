package cn.edu.hitsz.mapper;

import cn.edu.hitsz.pojo.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* @author pj
* @description 针对表【customers(客户表，记录客户基本信息)】的数据库操作Mapper
* @createDate 2025-10-17 22:34:02
* @Entity cn.edu.hitsz.pojo.Customers
*/
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

    /**
     * 根据客户姓名模糊查询客户列表
     * @param name 客户姓名
     * @return 客户列表
     */
    List<Customer> list(@Param("name") String name);
}




