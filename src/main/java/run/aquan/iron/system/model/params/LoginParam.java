package run.aquan.iron.system.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Class LoginParam
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/21 10:25
 * @Version 1.0
 **/
@Data
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 255, message = "用户名字符长度不能超过 {max}")
    private String username;

    @NotBlank(message = "登陆密码不能为空")
    @Size(max = 100, message = "用户密码字符长度不能超过 {max}")
    private String password;

}
