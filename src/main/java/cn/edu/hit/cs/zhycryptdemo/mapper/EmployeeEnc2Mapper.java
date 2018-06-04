package cn.edu.hit.cs.zhycryptdemo.mapper;

import cn.edu.hit.cs.zhycryptdemo.model.EmployeeEnc2;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeListReqVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeEnc2Mapper {

    List<EmployeeEnc2> listByPage(EmployeeListReqVo req);

    int insert(EmployeeEnc2 employeeEnc2);
}
