package cn.edu.hit.cs.zhycryptdemo.service;

import cn.edu.hit.cs.zhycryptdemo.mapper.TelEnc3AMapper;
import cn.edu.hit.cs.zhycryptdemo.model.TelEnc3A;
import cn.edu.hit.cs.zhycryptdemo.vo.req.TelAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.TelListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.TelListResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TelEnc3AService {

    @Autowired
    private TelEnc3AMapper telEnc3AMapper;

    public List<TelListResVo> listByPage(TelListReqVo req) {
        List<TelEnc3A> telEnc3AS = this.telEnc3AMapper.listByPage(req);
        List<TelListResVo> telListResVoList = telEnc3AS.stream()
                .map(o -> TelListResVo.builder()
                        .id(o.getId())
                        .employeeid(o.getEmployeeid())
                        .tel(o.getTelReplacement())
                        .build()
                ).collect(Collectors.toList());
        log.debug("TelEnc3AService.listByPage telListResVoList : {}", telListResVoList);
        return telListResVoList;
    }

    public int insert(TelAddReqVo req) {
        TelEnc3A telEnc3A = TelEnc3A.builder()
                .employeeid(req.getEmployeeid())
                .telReplacement(req.getTel())
                .userid(req.getUserid())
                .build();
        return this.telEnc3AMapper.insert(telEnc3A);
    }

}
