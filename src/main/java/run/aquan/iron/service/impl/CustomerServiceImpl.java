package run.aquan.iron.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.core.AbstractService;
import run.aquan.iron.dao.CustomerMapper;
import run.aquan.iron.model.Customer;
import run.aquan.iron.service.CustomerService;

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
