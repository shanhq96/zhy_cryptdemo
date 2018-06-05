package cn.edu.hit.cs.zhycryptdemo.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelListResVo {
    private int id;
    private String employeeid;
    private String tel;
}
