package cn.edu.hit.cs.zhycryptdemo.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAddReqVo extends BaseReq {
    @NotEmpty(message = "employeeid is empty")
    private String employeeid;
    @NotEmpty(message = "name is empty")
    private String name;
    @NotEmpty(message = "age is empty")
    private String age;
    @NotEmpty(message = "tel is empty")
    private String tel;
    @NotEmpty(message = "idnumber is empty")
    private String idnumber;
    @NotEmpty(message = "income is empty")
    private String income;
    @NotEmpty(message = "month is empty")
    private String month;
    @NotEmpty(message = "outcome is empty")
    private String outcome;
}
