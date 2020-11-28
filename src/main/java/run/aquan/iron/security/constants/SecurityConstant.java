package run.aquan.iron.security.constants;

/**
 * @Class SecurityConstant
 * @Description Seruity常量
 * @Author Saving
 * @Date 2019/12/19 16:31
 * @Version 1.0
 **/
public class SecurityConstant {

    /**
     * 登录的地址
     */
    public static final String AUTH_LOGIN_URL = "/auth/user/login";

    /**
     * 角色的key
     **/
    public static final String ROLE_CLAIMS = "rol";

    /**
     * rememberMe 为 false 的时候过期时间是1个小时
     */
    public static final long EXPIRATION = 60L * 60L;

    /**
     * rememberMe 为 true 的时候过期时间是7天
     */
    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /**
     * refreshToken过期时间是7天
     */
    public static final int EXPIRATION_REFRESHTOKEN = 60 * 60 * 24 * 7;

    /**
     * JWT签名密钥硬编码到应用程序代码中，应该存放在环境变量或.properties文件中。
     */
    public static final String JWT_SECRET_KEY = "092F2853DB047AD2D4AE2D79717129BD7ACA66E7F2CB1C11F292F301FF5EB805";

    /**
     * JWT签名密钥硬编码到应用程序代码中，应该存放在环境变量或.properties文件中。
     */
    public static final String JWT_REFRESHTOKEN_SECRET_KEY = "0AEBC71BAA3BF439ADF9F1F50158FB96CB26BD146B88B047C4A7548EAA7F6338";

    // JWT token defaults
    public static final String ADMIN_TOKEN_HEADER = "ADMIN-Authorization";
    public static final String TOKEN_HEADER = "Authorization-Access-Token";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    private SecurityConstant() {    }

}
