package run.aquan.iron.system.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Class RedisParam
 * @Description TODO
 * @Author Aquan
 * @Date 2020.7.19 22:40
 * @Version 1.0
 **/
@Data
public class RedisParam {

    @NotBlank(message = "Key不能为空")
    private String key;

    @NotBlank(message = "Value不能为空")
    private String value;

}
