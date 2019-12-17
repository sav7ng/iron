package run.aquan.iron.system.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Data
@Builder
public class Customer {

    @Id
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String company;

    private String address;

    private String city;

    private Integer state;

    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    private String phone;

    private String fax;

    private String email;

    @Column(name = "support_repld")
    private Integer supportRepld;

    @Getter
    @Setter
    private List<Invoice> invoices;
}