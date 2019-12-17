package run.aquan.iron.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultGenerator;
import run.aquan.iron.system.model.Customer;
import run.aquan.iron.system.service.CustomerService;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/08/10.
*/
@RestController
@RequestMapping("customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping("add")
    public Result add(Customer customer) {
        customerService.save(customer);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("delete")
    public Result delete(@RequestParam Integer id) {
        customerService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Customer customer) {
        customerService.update(customer);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("detail")
    public Result detail(@RequestParam Integer id) {
        Customer customer = customerService.findById(id);
        return ResultGenerator.genSuccessResult(customer);
    }

    @PostMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Customer> list = customerService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
