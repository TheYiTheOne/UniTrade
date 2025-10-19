package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Customer;
import cn.edu.hitsz.service.CustomerService;
import cn.edu.hitsz.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

/**
* @author pj
* @description 针对表【customers(客户表，记录客户基本信息)】的数据库操作Service实现
* @createDate 2025-10-17 22:34:02
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService {

}




