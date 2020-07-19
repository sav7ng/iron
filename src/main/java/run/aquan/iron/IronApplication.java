package run.aquan.iron;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("run.aquan.iron.system.mapper")
public class IronApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronApplication.class, args);
    }

}
