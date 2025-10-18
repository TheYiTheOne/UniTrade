package cn.edu.hitsz.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SaInterceptor(handler -> {
            // 登录校验 -- 拦截所有路由，并排除/login 用于开放登录
            SaRouter.match("/**", "/login", r -> StpUtil.checkLogin());


            // 根据路由划分模块，根据角色职能进行权限控制
            SaRouter.match("/products/**", r -> StpUtil.checkRole("clerk"));
            SaRouter.match("/customers/**", r -> StpUtil.checkRole("clerk"));
            SaRouter.match("/warehouses/**", r -> StpUtil.checkRole("clerk"));
            SaRouter.match("/inventory/**", r -> StpUtil.checkRole("clerk"));
            SaRouter.match("/orders/**", r -> StpUtil.checkRole("clerk"));

            SaRouter.match(SaHttpMethod.POST).match("/orders", r -> StpUtil.checkRole("manager"));
            SaRouter.match("/orders/submit/*", r -> StpUtil.checkRole("admin"));

        }))
        .addPathPatterns("/login","/products/**", "/customers/**", "/warehouses/**", "/inventory/**", "/orders/**")
        .excludePathPatterns("/error");
    }


}
