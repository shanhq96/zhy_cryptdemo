package cn.edu.hit.cs.zhycryptdemo.mapper;

import cn.edu.hit.cs.zhycryptdemo.model.OrderEnc1A;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderEnc1AMapper {

    // List<OrderEnc1A> listByPage(EmployeeListReqVo req);

    int insert(OrderEnc1A orderEnc1A);
}
