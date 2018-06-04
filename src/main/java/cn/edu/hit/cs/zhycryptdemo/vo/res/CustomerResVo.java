package cn.edu.hit.cs.zhycryptdemo.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResVo {
    private int id;
    private String name;
    private String age;
    private String tel;
    private String income;
    private String outcome;
    private String idnumber;
}
