package run.aquan.iron.model.params;

import lombok.Builder;
import lombok.Data;

/**
 * @Class WebhookParam
 * @Description TODO
 * @Author Aquan
 * @Date 2019/12/12 18:15
 * @Version 1.0
 **/
@Data
@Builder
public class WebhookParam {

    private String id;
    private String collection_id;
    private String paid;
    private String state;
    private String amount;
    private String paid_amount;
    private String due_at;
    private String email;
    private String mobile;
    private String name;
    private String url;
    private String paid_at;
    private String x_signature;

}
