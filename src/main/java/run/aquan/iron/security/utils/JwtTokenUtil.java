package run.aquan.iron.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import run.aquan.iron.security.constants.SecurityConstant;
import run.aquan.iron.security.entity.JwtUser;
import run.aquan.iron.system.model.dto.AuthToken;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.service.SysUserService;
import run.aquan.iron.system.service.UserService;
import run.aquan.iron.system.utils.IronDateUtil;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Class JwtTokenUtils
 * @Description TODO 生成足够的安全随机密钥，以适合符合规范的签名
 * @Author Aquan
 * @Date 2019/12/19 16:30
 * @Version 1.0
 **/
@Slf4j
@Component
public class JwtTokenUtil {

    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstant.JWT_SECRET_KEY);
    private static SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

    private final UserService userService;

    private final SysUserService sysUserService;

    public JwtTokenUtil(UserService userService, SysUserService sysUserService) {
        this.userService = userService;
        this.sysUserService = sysUserService;
    }

    public static JwtTokenUtil jwtTokenUtil;

    @PostConstruct
    public void init() {
        jwtTokenUtil = this;
    }

    public static AuthToken createToken(JwtUser jwtUser, Boolean rememberMe) {
        List<String> roles = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        long expiration = rememberMe ? SecurityConstant.EXPIRATION_REMEMBER : SecurityConstant.EXPIRATION;
        Long currentTimeMillis = System.currentTimeMillis() + expiration * 1000;
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), roles, currentTimeMillis);
        AuthToken authToken = AuthToken.builder().accessToken(token).expiration(IronDateUtil.asDate(currentTimeMillis)).build();
        return authToken;
    }

    public static String createToken(String username, List<String> roles, Long currentTimeMillis) {
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("typ", SecurityConstant.TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim(SecurityConstant.ROLE_CLAIMS, String.join(",", roles))
                .setIssuer("Aquan")
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(new Date(currentTimeMillis))
                .compact();
        return SecurityConstant.TOKEN_PREFIX + tokenPrefix;
    }

    private boolean tokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取用户所有角色
     */
    public static List<SimpleGrantedAuthority> getUserRolesByToken(String token) {
        String role = (String) getTokenBody(token)
                .get(SecurityConstant.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Date getTokenExpiration(String token) {
        Claims tokenBody = getTokenBody(token);
        return tokenBody.getExpiration();
    }

    public static Boolean checkToken(String token, Boolean admin) {
        Claims tokenBody = getTokenBody(token);
        if (admin) {
            SysUser sysUser = jwtTokenUtil.sysUserService.findUserByUserName(tokenBody.getSubject());
            return IronDateUtil.checkDate(sysUser.getExpirationTime(), tokenBody.getExpiration());
        } else {
            User user = jwtTokenUtil.userService.findUserByUserName(tokenBody.getSubject());
            return IronDateUtil.checkDate(user.getExpirationTime(), tokenBody.getExpiration());
        }
    }

}

