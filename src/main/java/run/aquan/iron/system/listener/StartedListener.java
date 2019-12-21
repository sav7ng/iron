package run.aquan.iron.system.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @Class StartedListener
 * @Description TODO 在应用程序启动后执行的方法
 * @Author Aquan
 * @Date 2019/12/20 18:18
 * @Version 1.0
 **/
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@PropertySource(value = {"classpath:application.yml"})
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${iron.baseUrl}")
    private String baseUrl;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        this.printStartInfo();
    }

    private void printStartInfo() {
            log.debug("Iron doc was enable at  {}/swagger-ui.html", baseUrl);
    }

}
