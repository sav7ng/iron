package run.aquan.iron.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.core.AbstractService;
import run.aquan.iron.system.dao.CustomerMapper;
import run.aquan.iron.system.model.Customer;
import run.aquan.iron.system.service.CustomerService;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/08/10.
 */
@Service
@Transactional
public class CustomerServiceImpl extends AbstractService<Customer> implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;

}
