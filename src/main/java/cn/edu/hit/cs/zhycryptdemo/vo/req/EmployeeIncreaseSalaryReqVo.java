package cn.edu.hit.cs.zhycryptdemo.vo.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeIncreaseSalaryReqVo extends BaseReq {
    @NotNull(message = "id is null")
    private Integer id;

    @Range(min = 1, max = 100, message = "比率只能在1%到100%之间")
    private Integer rate;

    @Min(value = 1, message = "最小涨薪为1")
    private Integer number;
}
