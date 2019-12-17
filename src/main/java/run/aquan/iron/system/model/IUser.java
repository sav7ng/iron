package run.aquan.iron.system.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Class IUser
 * @Description TODO
 * @Author Aquan
 * @Date 2019.8.13 1:06
 * @Version 1.0
 **/
@Data
@Builder
public class IUser {

    private String id;

    private String name;

    private Integer age;

}