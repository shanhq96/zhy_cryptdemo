package cn.edu.hit.cs.zhycryptdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelEnc3A {

    private int id;
    private String employeeid;
    private String telReplacement;
    private String userid;
}
