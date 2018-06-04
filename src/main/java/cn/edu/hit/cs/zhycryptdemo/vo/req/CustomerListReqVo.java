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
    private int incomeMin;
    private int incomeMax;
    private int outcomeMin;
    private int outcomeMax;
}
