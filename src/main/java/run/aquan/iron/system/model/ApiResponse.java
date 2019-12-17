package run.aquan.iron.system.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Class ApiResponse
 * @Description TODO
 * @Author Aquan
 * @Date 2019.8.13 0:40
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
public class ApiResponse {
    private int errCode = 0;

    private String errMsg;

    private Object data;

    public ApiResponse(Object data) {
        this.data = data;
    }

}