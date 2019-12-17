package run.aquan.iron.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.core.AbstractService;
import run.aquan.iron.system.dao.InvoiceMapper;
import run.aquan.iron.system.model.Invoice;
import run.aquan.iron.system.service.InvoiceService;

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
