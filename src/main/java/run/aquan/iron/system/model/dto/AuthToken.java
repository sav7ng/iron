package run.aquan.iron.system.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Class AuthToken
 * @Description Token
 * @Author Saving
 * @Date 2019-12-26 11:42:38
 * @Version 1.1
 **/
@Data
@Builder
public class AuthToken {

    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Create Time.
     */
    @JsonProperty("expiration")
    private Date expiration;

    /**
     * Refresh token.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
