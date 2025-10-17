package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Customer;
import cn.edu.hitsz.service.CustomerService;
import cn.edu.hitsz.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【customers(客户表，记录客户基本信息)】的数据库操作Service实现
* @createDate 2025-10-18 00:20:57
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService{

}




