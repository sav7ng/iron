package run.aquan.iron.system.constants;

/**
 * @Class IronConstant
 * @Description 项目常量
 * @Author Saving
 * @Date 2019/12/24 14:24
 * @Version 1.0
 **/
public final class IronConstant {

    public static final String BASE_PACKAGE = "run.aquan.iron.system";

    public static final String ADMIN_CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller.admin";

    public static final String CONTENT_CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller.content";

    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    public static final String REDIS_REFRESHTOKEN_PREFIX = "userRefreshToken:";

}
