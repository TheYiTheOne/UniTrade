package cn.edu.hitsz.interceptor;


import cn.edu.hitsz.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (token == null || token.isEmpty()) {
            writeResponse(response, 0, "NOT_LOGIN");
            return false;
        }

        try {
            if (JwtUtils.isTokenExpired(token)) {
                writeResponse(response, 0, "TOKEN_EXPIRED");
                return false;
            }

            Claims claims = JwtUtils.getClaimsFromToken(token);
            request.setAttribute("claims", claims); // 后续可获取用户信息
            return true;
        } catch (RuntimeException e) {
            writeResponse(response, 0, "NOT_LOGIN");
            return false;
        }
    }

    private void writeResponse(HttpServletResponse response, int code, String msg) throws java.io.IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);
        response.getWriter().write(String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":null}", code, msg));
    }
}