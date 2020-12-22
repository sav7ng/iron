package run.aquan.iron.system.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * swagger的属性配置类
 */
@Data
@ConfigurationProperties(prefix = "iron.swagger")
public class SwaggerProperties {

    /**
     * 前台接口配置
     */
    private SwaggerEntity front;

    /**
     * 后台接口配置
     */
    private SwaggerEntity back;

    /**
     * 是否开启swagger服务
     */
    private Boolean enable;

    @Data
    public static class SwaggerEntity {
        private String groupName;
        private String basePackage;
        private String title;
        private String description;
        private String contactName;
        private String contactEmail;
        private String contactUrl;
        private String version;
    }
}
