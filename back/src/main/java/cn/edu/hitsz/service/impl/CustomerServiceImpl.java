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

    /**
     * 根据客户姓名检查是否存在
     * @param name 客户姓名
     * @return 是否存在
     */
    public boolean existsByName(String name) {
        return this.lambdaQuery()
                .eq(Customer::getName, name)
                .count() > 0;
    }

    /**
     * 根据客户姓名检查是否存在（排除指定ID）
     * @param name 客户姓名
     * @param id 要排除的ID
     * @return 是否存在
     */
    public boolean existsByNameAndIdNot(String name, Integer id) {
        return this.lambdaQuery()
                .eq(Customer::getName, name)
                .ne(Customer::getId, id)
                .count() > 0;
    }

    /**
     * 根据客户电话检查是否存在
     * @param phone 客户电话
     * @return 是否存在
     */
    public boolean existsByPhone(String phone) {
        return this.lambdaQuery()
                .eq(Customer::getPhone, phone)
                .count() > 0;
    }

    /**
     * 根据客户电话检查是否存在（排除指定ID）
     * @param phone 客户电话
     * @param id 要排除的ID
     * @return 是否存在
     */
    public boolean existsByPhoneAndIdNot(String phone, Integer id) {
        return this.lambdaQuery()
                .eq(Customer::getPhone, phone)
                .ne(Customer::getId, id)
                .count() > 0;
    }
}




