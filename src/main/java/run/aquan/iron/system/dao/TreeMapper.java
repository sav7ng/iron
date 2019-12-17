package run.aquan.iron.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import run.aquan.iron.system.model.Customer;
import run.aquan.iron.system.model.Invoice;

@Mapper
public interface TreeMapper extends BaseMapper {

    Invoice getInvoiceById(Integer id);

    Customer getCustomerById(Integer id);

}
