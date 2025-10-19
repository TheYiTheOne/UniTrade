package cn.edu.hitsz.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.hitsz.common.Permission;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SaInterceptor(handler -> {

            // =========== 1. 登录校验 ===========
            SaRouter.match("/**", "/login", r -> StpUtil.checkLogin());

            // =========== 2. 查询模块权限校验 ===========
            SaRouter.match("/products/**", r -> StpUtil.checkPermission(Permission.VIEW_DATA()));
            SaRouter.match("/customers/**", r -> StpUtil.checkPermission(Permission.VIEW_DATA()));
            SaRouter.match("/warehouses/**", r -> StpUtil.checkPermission(Permission.VIEW_DATA()));
            SaRouter.match("/inventory/**", r -> StpUtil.checkPermission(Permission.VIEW_DATA()));
            SaRouter.match("/orders/**", r -> StpUtil.checkPermission(Permission.VIEW_DATA()));

            // =========== 3. 销售单权限校验（开单、审核、收款、退货） ===========
            SaRouter.match(SaHttpMethod.POST).match("/orders", r -> StpUtil.checkPermission(Permission.OPEN_ORDER()));
            SaRouter.match(SaHttpMethod.PUT).match("/orders/submit/*", r -> StpUtil.checkPermission(Permission.AUDIT()));
            SaRouter.match(SaHttpMethod.PUT).match("/orders/pay/*", r -> StpUtil.checkPermission(Permission.RECEIVE_PAYMENT()));
            SaRouter.match(SaHttpMethod.PUT).match("/orders/cancel/*", r -> StpUtil.checkPermission(Permission.RETURN_GOODS()));

            // =========== 4. 库存权限校验（进货入库、转移库存） ===========
            SaRouter.match(SaHttpMethod.PUT).match("/inventory/add", r -> StpUtil.checkPermission(Permission.INVENTORY_IN()));
            SaRouter.match(SaHttpMethod.PUT).match("/inventory/transfer", r -> StpUtil.checkPermission(Permission.TRANSFER_STOCK()));

        }))
        .addPathPatterns("/products/**", "/customers/**", "/warehouses/**", "/inventory/**", "/orders/**")
        .excludePathPatterns("/error");
    }


}
