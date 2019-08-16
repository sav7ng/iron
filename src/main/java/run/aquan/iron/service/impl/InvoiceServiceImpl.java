package run.aquan.iron.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.core.AbstractService;
import run.aquan.iron.dao.InvoiceMapper;
import run.aquan.iron.model.Invoice;
import run.aquan.iron.service.InvoiceService;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/08/10.
 */
@Service
@Transactional
public class InvoiceServiceImpl extends AbstractService<Invoice> implements InvoiceService {
    @Resource
    private InvoiceMapper invoiceMapper;

}
