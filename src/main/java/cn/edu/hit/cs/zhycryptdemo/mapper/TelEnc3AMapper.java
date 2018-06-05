package cn.edu.hit.cs.zhycryptdemo.mapper;

import cn.edu.hit.cs.zhycryptdemo.model.TelEnc3A;
import cn.edu.hit.cs.zhycryptdemo.vo.req.TelListReqVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TelEnc3AMapper {

    List<TelEnc3A> listByPage(TelListReqVo req);

    int insert(TelEnc3A telEnc3A);
}
