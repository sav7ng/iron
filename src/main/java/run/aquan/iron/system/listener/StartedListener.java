package run.aquan.iron.system.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import run.aquan.iron.system.job.TestJob;
import run.aquan.iron.system.service.RoleService;
import run.aquan.iron.system.service.SysUserService;

/**
 * @Class StartedListener
 * @Description Method to execute after application launch
 * @Author Saving
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

    private final SysUserService sysUserService;

    private final RoleService roleService;

    @Autowired
    public StartedListener(SysUserService sysUserService, RoleService roleService) {
        this.sysUserService = sysUserService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        this.initAdmin();
        this.initRole();
        this.printStartInfo();
        // this.initTestJob();定时任务test
    }

    private void printStartInfo() {
        log.debug("Iron doc was enable at {}/swagger-ui", baseUrl);
    }

    private void initAdmin() {
        String init = sysUserService.init();
        log.info("Admin Whether initialization was successful:" + init);
    }

    private void initRole() {
        String init = roleService.init();
        log.info("User Role Whether initialization was successful:" + init);
    }

    private void initTestJob() {
        TestJob.createJob(1);
    }

}
