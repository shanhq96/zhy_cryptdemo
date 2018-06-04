package cn.edu.hit.cs.zhycryptdemo.mapper;

import cn.edu.hit.cs.zhycryptdemo.model.CustomerEnc1;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerEnc1Mapper {

    List<CustomerEnc1> listByPage();

    int insert(CustomerEnc1 customerEnc1);
}
