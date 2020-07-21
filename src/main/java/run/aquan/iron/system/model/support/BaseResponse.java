package run.aquan.iron.system.model.support;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @Class BaseResponse
 * @Description TODO
 * @Author Aquan
 * @Date 2020/7/10 15:05
 * @Version 1.0
 **/
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    /**
     * Response status.
     */
    private Integer status;

    /**
     * Response message.
     */
    private String message;

    /**
     * Response development message
     */
    private String devMessage;

    /**
     * Response result
     */
    private T result;

    public BaseResponse(Integer status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    /**
     * Creates an ok result with message and data. (Default status is 200)
     *
     * @param result    result data
     * @param message result message
     * @return ok result with message and result
     */
    @NonNull
    public static <T> BaseResponse<T> ok(@Nullable String message, @Nullable T result) {
        return new BaseResponse<>(HttpStatus.OK.value(), message, result);
    }

    /**
     * Creates an ok result with message only. (Default status is 200)
     *
     * @param message result message
     * @return ok result with message only
     */
    @NonNull
    public static <T> BaseResponse<T> ok(@Nullable String message) {
        return ok(message, null);
    }

    /**
     * Creates an ok result with data only. (Default message is OK, status is 200)
     *
     * @param result result to response
     * @param <T>  result type
     * @return base response with data
     */
    public static <T> BaseResponse<T> ok(@NonNull T result) {
        return new BaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), result);
    }
}
