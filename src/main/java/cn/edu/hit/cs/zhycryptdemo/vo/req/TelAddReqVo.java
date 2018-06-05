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
public class TelAddReqVo extends BaseReq {
    @NotEmpty(message = "employeeid is empty")
    private String employeeid;
    @NotEmpty(message = "tel is empty")
    private String tel;
}
