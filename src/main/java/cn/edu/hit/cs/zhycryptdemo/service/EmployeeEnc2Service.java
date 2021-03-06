package cn.edu.hit.cs.zhycryptdemo.service;

import cn.edu.hit.cs.zhycryptdemo.common.AESUtils;
import cn.edu.hit.cs.zhycryptdemo.common.PaillierUtils;
import cn.edu.hit.cs.zhycryptdemo.mapper.EmployeeEnc2Mapper;
import cn.edu.hit.cs.zhycryptdemo.model.EmployeeEnc2;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeIncreaseSalaryReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.EmployeeListResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
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
                // .ageHom(BigInteger.ONE)
                .telEnc(req.getTel())
                .incomeEnc(req.getIncome())
                // .incomeHom(BigInteger.ONE)
                .monthEnc(req.getMonth())
                // .monthHom(BigInteger.ONE)
                .outcomeEnc(req.getOutcome())
                // .outcomeHom(BigInteger.ONE)
                .idnumberEnc(req.getIdnumber())
                .userid(req.getUserid())
                .build();
        return this.employeeEnc2Mapper.insert(employeeEnc2);
    }

    public boolean increaseSalary(EmployeeIncreaseSalaryReqVo req) {
        boolean flag = false;
        EmployeeEnc2 employeeEnc2 = this.employeeEnc2Mapper.findById(req.getId());
        int originalIncomeInt = Integer.parseInt(employeeEnc2.getIncomeEnc());
        int newIncomeInt = originalIncomeInt;
        if (null != req.getNumber()) {
            //当两个参数同时存在时，优先通过固定数值涨薪
            newIncomeInt = originalIncomeInt + req.getNumber();
        } else if (null != req.getRate()) {
            newIncomeInt = originalIncomeInt * (100 + req.getRate()) / 100;
        }
        log.debug("id{}, originalIncome {}, newIncome {}, number {}, rate {}", req.getId(), originalIncomeInt, newIncomeInt, req.getNumber(), req.getRate());
        employeeEnc2.setIncomeHom(PaillierUtils.getInstance().Encryption(BigInteger.valueOf(newIncomeInt)));
        employeeEnc2.setIncomeEnc(AESUtils.encrypt(String.valueOf(newIncomeInt)));
        int uc = this.employeeEnc2Mapper.updateIncome(employeeEnc2);
        flag = uc > 0;
        return flag;
    }

    /**
     * 排序
     * @param req
     * @return
     */
    public List<EmployeeEnc2> sort(EmployeeListReqVo req) {
        List<EmployeeEnc2> employeeEnc2List = this.employeeEnc2Mapper.listByPage(req);
        List<EmployeeEnc2> employeeEnc2ListSorted = employeeEnc2List.stream().sorted(
                (x, y) -> compareHom(x.getIncomeHom(), y.getIncomeHom())
        ).collect(Collectors.toList());
        return employeeEnc2ListSorted;
    }

    private int compareHom(BigInteger xHom, BigInteger yHom) {
        //if x >= y , return 1
        // if x < y , return -1

        Random random = new Random(System.currentTimeMillis());
        BigInteger r1 = new BigInteger(String.valueOf(random.nextInt(String.valueOf(PaillierUtils.getInstance().n).length() / 8) + (String.valueOf(PaillierUtils.getInstance().n).length() / 8)));
        BigInteger r2 = new BigInteger(String.valueOf(random.nextInt(String.valueOf(PaillierUtils.getInstance().n).length() / 8)));
        BigInteger x1Hom = xHom.multiply(PaillierUtils.getInstance().Encryption(BigInteger.ONE));
        BigInteger y1Hom = yHom.pow(2);

        BigInteger lHom = x1Hom.multiply(y1Hom.pow(PaillierUtils.getInstance().n.subtract(BigInteger.ONE).intValue())).pow(r1.intValue()).multiply(PaillierUtils.getInstance().Encryption(r2));
        BigInteger l = PaillierUtils.getInstance().Decryption(lHom);
        int u = l.compareTo(PaillierUtils.getInstance().n.divide(BigInteger.valueOf(2))) > 0 ? 1 : 0;
        if (u == 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
