package run.aquan.iron.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Class BillingInfo
 * @Description TODO
 * @Author Aquan
 * @Date 2019.8.10 19:54
 * @Version 1.0
 **/
@Data
@Builder
public class BillingInfo {

    private String billingAddress;
    private String billingCity;
    private Integer billingState;
    private String billingCountry;
    private String billingPostalCode;

}