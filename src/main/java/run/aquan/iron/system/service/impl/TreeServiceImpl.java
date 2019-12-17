package run.aquan.iron.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.dao.TreeMapper;
import run.aquan.iron.system.model.Customer;
import run.aquan.iron.system.model.Invoice;
import run.aquan.iron.system.service.TreeService;

/**
 * @Class TreeServiceImpl
 * @Description TODO
 * @Author Aquan
 * @Date 2019.8.10 21:35
 * @Version 1.0
 **/
@Service
@Transactional
public class TreeServiceImpl implements TreeService {

    @Autowired
    TreeMapper treeMapper;

    @Override
    public Invoice getInvoiceById(Integer id) {
        return treeMapper.getInvoiceById(id);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return treeMapper.getCustomerById(id);
    }
}