package run.aquan.iron.system.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Class ChangePasswordParam
 * @Description TODO
 * @Author Aquan
 * @Date 2020/3/25 11:38
 * @Version 1.0
 **/
@Data
public class ChangePasswordParam {

    @NotBlank(message = "旧密码不能为空")
    private String password;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}

