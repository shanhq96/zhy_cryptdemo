package cn.edu.hit.cs.zhycryptdemo.service;

import cn.edu.hit.cs.zhycryptdemo.mapper.EmployeeEnc2Mapper;
import cn.edu.hit.cs.zhycryptdemo.model.EmployeeEnc2;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.EmployeeListResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeEnc2Service {

    @Autowired
    private EmployeeEnc2Mapper employeeEnc2Mapper;

    public List<EmployeeListResVo> listByPage(EmployeeListReqVo req) {
        List<EmployeeEnc2> employeeEnc2List = this.employeeEnc2Mapper.listByPage(req);
        List<EmployeeListResVo> employeeListResVoList = employeeEnc2List.stream()
                .map(o -> EmployeeListResVo.builder()
                        .id(o.getId())
                        .employeeid(o.getEmployeeid())
                        .name(o.getNameEnc())
                        .age(o.getAgeEnc())
                        .tel(o.getTelEnc())
                        .income(o.getIncomeEnc())
                        .month(o.getMonthEnc())
                        .outcome(o.getOutcomeEnc())
                        .idnumber(o.getIdnumberEnc())
                        .build()
                ).collect(Collectors.toList());
        log.debug("EmployeeEnc2Service.listByPage employeeListResVoList : {}", employeeListResVoList);
        return employeeListResVoList;
    }

    public int insert(EmployeeAddReqVo req) {
        EmployeeEnc2 employeeEnc2 = EmployeeEnc2.builder()
                .employeeid(req.getEmployeeid())
                .nameEnc(req.getName())
                .ageEnc(req.getAge())
                .ageHom(BigInteger.ONE)
                .telEnc(req.getTel())
                .incomeEnc(req.getIncome())
                .incomeHom(BigInteger.ONE)
                .monthEnc(req.getMonth())
                .monthHom(BigInteger.ONE)
                .outcomeEnc(req.getOutcome())
                .outcomeHom(BigInteger.ONE)
                .idnumberEnc(req.getIdnumber())
                .userid(req.getUserid())
                .build();
        return this.employeeEnc2Mapper.insert(employeeEnc2);
    }

}