package run.aquan.iron.system.service;

import run.aquan.iron.system.model.Customer;
import run.aquan.iron.system.model.Invoice;

public interface TreeService {

    Invoice getInvoiceById(Integer id);

    Customer getCustomerById(Integer id);

}
