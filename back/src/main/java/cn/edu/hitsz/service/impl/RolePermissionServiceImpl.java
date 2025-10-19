package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.common.Permission;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.RolePermission;
import cn.edu.hitsz.service.RolePermissionService;
import cn.edu.hitsz.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* @author Administrator
* @description 针对表【role_permission(角色权限表，定义角色拥有哪些权限)】的数据库操作Service实现
* @createDate 2025-10-18 00:20:34
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

    // ✅ 使用 ConcurrentHashMap 实现简单本地缓存：roleId -> bitmask
    private final Map<Integer, Integer> permissionBitmaskCache = new ConcurrentHashMap<>();

    // ===================== 业务方法 =====================

    @Override
    public int getPermissionBitmask(int roleId) {
        return getRolePermissionBitmask(roleId);
    }

    @Override
    public boolean hasPermission(int roleId, int permissionId) {
        int permMask = Permission.fromId(permissionId).getBitMask();
        int roleMask = getRolePermissionBitmask(roleId);
        return (roleMask & permMask) != 0;
    }

    @Override
    public boolean hasPermissionByCode(int roleId, String permissionCode) {
        int permMask = Permission.fromCode(permissionCode).getBitMask();
        int roleMask = getRolePermissionBitmask(roleId);
        return (roleMask & permMask) != 0;
    }

    @Override
    public List<Integer> getPermissionIds(int roleId) {
        int mask = getRolePermissionBitmask(roleId);
        List<Integer> result = new ArrayList<>();
        for (Permission p : Permission.values()) {
            if ((mask & p.getBitMask()) != 0) {
                result.add(p.getId());
            }
        }
        return result;
    }

    @Override
    public List<String> getPermissionCodes(int roleId) {
        int mask = getRolePermissionBitmask(roleId);
        List<String> result = new ArrayList<>();
        for (Permission p : Permission.values()) {
            if ((mask & p.getBitMask()) != 0) {
                result.add(p.getCode());
            }
        }
        return result;
    }

    @Override
    public void refreshCache() {
        permissionBitmaskCache.clear();
    }

    // ===================== 私有方法 =====================

    /**
     * 获取角色权限位掩码（带缓存）
     */
    private int getRolePermissionBitmask(int roleId) {
        int result = permissionBitmaskCache.computeIfAbsent(roleId, this::loadBitmaskFromDb);

        return permissionBitmaskCache.computeIfAbsent(roleId, this::loadBitmaskFromDb);
    }

    /**
     * 从数据库加载角色权限位掩码
     */
    private int loadBitmaskFromDb(int roleId) {
        List<RolePermission> permissions = lambdaQuery()
                .eq(RolePermission::getRoleId, roleId)
                .list();
        System.out.println(permissions);

        int mask = 0;
        for (RolePermission rp : permissions) {
            try {
                Permission p = Permission.fromId(rp.getPermissionId());
                mask |= p.getBitMask();
            } catch (IllegalArgumentException e) {
                // 忽略无效权限 ID（如数据库脏数据）
                System.err.println("无效权限 ID: " + rp.getPermissionId());
            }
        }
        return mask;
    }

    // ===================== 静态工具方法（可选）=====================

    /**
     * 工具方法：判断指定的权限掩码是否包含某个权限 ID
     */
    public static boolean hasPermissionInMask(int mask, int permissionId) {
        int permMask = Permission.fromId(permissionId).getBitMask();
        return (mask & permMask) != 0;
    }

    /**
     * 工具方法：判断指定的权限掩码是否包含某个权限码
     */
    public static boolean hasPermissionInMaskByCode(int mask, String permissionCode) {
        int permMask = Permission.fromCode(permissionCode).getBitMask();
        return (mask & permMask) != 0;
    }
}




