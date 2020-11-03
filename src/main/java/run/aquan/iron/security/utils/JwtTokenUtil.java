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
import run.aquan.iron.system.constants.IronConstant;
import run.aquan.iron.system.model.dto.AuthToken;
import run.aquan.iron.system.model.entity.SysUser;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.service.SysUserService;
import run.aquan.iron.system.service.UserService;
import run.aquan.iron.system.utils.IronDateUtils;
import run.aquan.iron.system.utils.JedisUtils;

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

    private static byte[] apiRefreshTokenKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstant.JWT_REFRESHTOKEN_SECRET_KEY);
    private static SecretKey refreshTokenSecretKey = Keys.hmacShaKeyFor(apiRefreshTokenKeySecretBytes);

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

    public static AuthToken createToken(JwtUser jwtUser) {
        List<String> roles = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Long currentTimeMillis = System.currentTimeMillis();
        Long tokenExpirationTime = currentTimeMillis + SecurityConstant.EXPIRATION * 1000;
        Long refreshTokenExpirationTime = currentTimeMillis + SecurityConstant.EXPIRATION_REFRESHTOKEN * 1000;
        Date issuedTime = new Date();
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), roles, tokenExpirationTime, issuedTime);
        String refreshToken = JwtTokenUtil.createRefreshToken(jwtUser.getUsername(), refreshTokenExpirationTime, issuedTime);
        JedisUtils.setObject(IronConstant.REDIS_REFRESHTOKEN_PREFIX + jwtUser.getUsername(), refreshToken, SecurityConstant.EXPIRATION_REFRESHTOKEN);
        return AuthToken.builder().accessToken(token).expiration(IronDateUtils.asDate(tokenExpirationTime)).refreshToken(refreshToken).build();
    }

    public static String createRefreshToken(String username, Long currentTimeMillis, Date issuedTime) {
        return Jwts.builder()
                .setHeaderParam("typ", SecurityConstant.TOKEN_TYPE)
                .signWith(refreshTokenSecretKey, SignatureAlgorithm.HS256)
                .claim(SecurityConstant.ROLE_CLAIMS, "RefreshToken")
                .setIssuer("Saving")
                .setIssuedAt(issuedTime)
                .setSubject(username)
                .setExpiration(new Date(currentTimeMillis))
                .compact();
    }

    public static String createToken(String username, List<String> roles, Long currentTimeMillis, Date issuedTime) {
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("typ", SecurityConstant.TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim(SecurityConstant.ROLE_CLAIMS, String.join(",", roles))
                .setIssuer("Saving")
                .setIssuedAt(issuedTime)
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

    public static Claims getRefreshTokenBody(String refreshToken) {
        return Jwts.parser()
                .setSigningKey(refreshTokenSecretKey)
                .parseClaimsJws(refreshToken)
                .getBody();
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
            return IronDateUtils.checkDate(sysUser.getExpirationTime(), tokenBody.getExpiration());
        } else {
            User user = jwtTokenUtil.userService.findUserByUserName(tokenBody.getSubject());
            return IronDateUtils.checkDate(user.getExpirationTime(), tokenBody.getExpiration());
        }
    }

}

