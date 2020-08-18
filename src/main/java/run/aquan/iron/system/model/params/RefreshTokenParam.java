package run.aquan.iron.system.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Class RefreshTokenParam
 * @Description TODO
 * @Author Aquan
 * @Date 2020/7/20 17:38
 * @Version 1.0
 **/
@Data
public class RefreshTokenParam {

    @NotBlank(message = "refreshToken不能为空")
    public String refreshToken;

}
