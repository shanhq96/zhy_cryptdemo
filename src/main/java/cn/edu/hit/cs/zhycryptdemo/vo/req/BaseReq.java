package cn.edu.hit.cs.zhycryptdemo.vo.req;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BaseReq{
    @NotNull(message = "用户id为空")
    private String userid;
}
