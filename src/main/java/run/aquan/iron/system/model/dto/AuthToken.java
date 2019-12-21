package run.aquan.iron.system.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Class AuthToken
 * @Description TODO Token
 * @Author Aquan
 * @Date 2019/12/21 10:56
 * @Version 1.0
 **/
@Data
@Builder
public class AuthToken {

    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

}
