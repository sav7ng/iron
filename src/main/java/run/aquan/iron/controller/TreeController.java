package run.aquan.iron.controller;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.core.Result;
import run.aquan.iron.core.ResultGenerator;
import run.aquan.iron.service.IUserService;
import run.aquan.iron.service.TreeService;

/**
 * @Class TreeController
 * @Description TODO
 * @Author Aquan
 * @Date 2019.8.10 21:32
 * @Version 1.0
 **/
@RestController
@RequestMapping("/Tree")
public class TreeController {

    @Autowired
    TreeService treeService;

    @Autowired
    IUserService iUserService;

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size,
                       @RequestParam(defaultValue = "0") Integer id) {
        PageHelper.startPage(page, size);
        //List<Invoice> list = treeService.getInvoiceById(id);
        //PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(treeService.getInvoiceById(id));
    }

}