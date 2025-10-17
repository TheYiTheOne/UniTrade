package cn.edu.hitsz.utils;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET = "your-super-secure-256bit-secret-here!"; // 至少32字符
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 24小时

    /**
     * 生成 JWT 令牌
     */
    public static String generateToken(Integer id, String account, String name, Integer roleId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("account", account);
        claims.put("name", name);
        claims.put("roleId", roleId);

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(account)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /**
     * 解析 Token
     */
    public static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("TOKEN_EXPIRED");
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new RuntimeException("INVALID_TOKEN");
        }
    }

    // 获取字段
    public static Integer getUserId(String token) {
        return getClaimsFromToken(token).get("id", Integer.class);
    }

    public static String getUserAccount(String token) {
        return getClaimsFromToken(token).get("account", String.class);
    }

    public static String getUserName(String token) {
        return getClaimsFromToken(token).get("name", String.class);
    }

    public static Integer getUserRoleId(String token) {
        return getClaimsFromToken(token).get("roleId", Integer.class);
    }

    public static boolean isTokenExpired(String token) {
        try {
            final Date expiration = getClaimsFromToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (RuntimeException e) {
            return true;
        }
    }
}