package run.aquan.iron.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.aquan.iron.model.Customer;
import run.aquan.iron.model.Invoice;
import org.mapstruct.Mapper;

@Mapper
public interface TreeMapper extends BaseMapper {

    Invoice getInvoiceById(Integer id);

    Customer getCustomerById(Integer id);

}
