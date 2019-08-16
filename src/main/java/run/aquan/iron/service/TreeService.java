package run.aquan.iron.service;

import run.aquan.iron.model.Customer;
import run.aquan.iron.model.Invoice;

public interface TreeService {

    Invoice getInvoiceById(Integer id);

    Customer getCustomerById(Integer id);

}
