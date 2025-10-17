package cn.edu.hitsz.unitrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.unitrade.model.entity.User;
import cn.edu.hitsz.unitrade.service.UserService;
import cn.edu.hitsz.unitrade.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author pj
* @description 针对表【users】的数据库操作Service实现
* @createDate 2025-10-17 17:23:36
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User>
    implements UserService {

}




