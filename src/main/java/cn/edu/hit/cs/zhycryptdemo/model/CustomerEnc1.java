package cn.edu.hit.cs.zhycryptdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEnc1 {
    private Integer id;
    private String nameEnc;
    private String ageEnc;
    private String telEnc;
    private String idnumberEnc;
    private String incomeEnc;
    private String outcomeEnc;
    private String userid;
}
