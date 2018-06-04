package cn.edu.hit.cs.zhycryptdemo.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerListReqVo extends BaseReq {
    private String name;
    private Integer incomeMin;
    private Integer incomeMax;
    private Integer outcomeMin;
    private Integer outcomeMax;
}
