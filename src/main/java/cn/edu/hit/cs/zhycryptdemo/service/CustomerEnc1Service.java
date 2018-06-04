package cn.edu.hit.cs.zhycryptdemo.service;

import cn.edu.hit.cs.zhycryptdemo.mapper.CustomerEnc1Mapper;
import cn.edu.hit.cs.zhycryptdemo.model.CustomerEnc1;
import cn.edu.hit.cs.zhycryptdemo.vo.req.CustomerAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.CustomerListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.CustomerListResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerEnc1Service {

    @Autowired
    private CustomerEnc1Mapper customerEnc1Mapper;

    public List<CustomerListResVo> listByPage(CustomerListReqVo req) {
        List<CustomerEnc1> customerEnc1s = this.customerEnc1Mapper.listByPage(req);
        List<CustomerListResVo> customerListResVoList = customerEnc1s.stream()
                .map(o -> CustomerListResVo.builder()
                        .id(o.getId())
                        .name(o.getNameEnc())
                        .age(o.getAgeEnc())
                        .tel(o.getTelEnc())
                        .income(o.getIncomeEnc())
                        .outcome(o.getOutcomeEnc())
                        .idnumber(o.getIdnumberEnc())
                        .build()
                ).collect(Collectors.toList());
        log.debug("CustomerEnc1Service.listByPage customerListResVoList : {}", customerListResVoList);
        return customerListResVoList;
    }

    public int insert(CustomerAddReqVo req) {
        CustomerEnc1 customerEnc1 = CustomerEnc1.builder()
                .nameEnc(req.getName())
                .ageEnc(req.getAge())
                .telEnc(req.getTel())
                .incomeEnc(req.getIncome())
                .outcomeEnc(req.getOutcome())
                .idnumberEnc(req.getIdnumber())
                .userid(req.getUserid())
                .build();
        return this.customerEnc1Mapper.insert(customerEnc1);
    }

}
