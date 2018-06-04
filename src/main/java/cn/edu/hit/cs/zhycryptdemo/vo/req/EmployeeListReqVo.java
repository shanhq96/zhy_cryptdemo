package cn.edu.hit.cs.zhycryptdemo.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeListReqVo extends BaseReq {
    private String employeeid;
    private String name;
    private int incomeMin;
    private int incomeMax;
    private int sumIncomeMin;
    private int sumIncomeMax;
}
