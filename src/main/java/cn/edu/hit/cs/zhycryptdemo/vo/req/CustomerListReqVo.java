package cn.edu.hit.cs.zhycryptdemo.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerListReqVo extends BaseReq {
    private String name;
    @Null(message = "本表加密方式不允许排序操作")
    private Integer incomeMin;
    @Null(message = "本表加密方式不允许排序操作")
    private Integer incomeMax;
    @Null(message = "本表加密方式不允许排序操作")
    private Integer outcomeMin;
    @Null(message = "本表加密方式不允许排序操作")
    private Integer outcomeMax;
}
