package cn.edu.hit.cs.zhycryptdemo.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeListResVo {

    private Integer id;
    private String employeeid;
    private String name;
    private String age;
    private String tel;
    private String idnumber;
    private String income;
    private String month;
    private String outcome;

}
