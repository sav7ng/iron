package run.aquan.iron.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class Invoice {
    @Id
    @Column(name = "invoice_id")
    private Integer invoiceId;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "billing_city")
    private String billingCity;

    @Column(name = "billing_state")
    private Integer billingState;

    @Column(name = "billing_country")
    private String billingCountry;

    @Column(name = "billing_postalCode")
    private String billingPostalcode;

    private BigDecimal total;

    @Column(name = "customer_id")
    private Integer customerId;

    private Customer customer;

    private BillingInfo billingInfo;

}