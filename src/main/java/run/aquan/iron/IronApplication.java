package run.aquan.iron;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("run.aquan.iron.system.dao")
@EnableScheduling
@EnableSwagger2
public class IronApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronApplication.class, args);
    }

}
