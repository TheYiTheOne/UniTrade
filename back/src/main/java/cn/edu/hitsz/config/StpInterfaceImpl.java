package cn.edu.hitsz.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jayden
 * @Datetime Created in 14:09 2025/10/18
 * @Description 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合 
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        Integer roleId = (Integer) StpUtil.getSession().get("roleId");
        List<String> list = new ArrayList<>();

        if(roleId == 0){
            list.add("admin");
            list.add("manager");
            list.add("clerk");
        } else if(roleId == 1){
            list.add("manager");
            list.add("clerk");
        } else if(roleId == 2){
            list.add("clerk");
        }

        return list;
    }

}
