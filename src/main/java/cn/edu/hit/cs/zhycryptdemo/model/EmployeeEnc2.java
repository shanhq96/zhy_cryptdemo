package cn.edu.hit.cs.zhycryptdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEnc2 {
    private Integer id;
    private String employeeid;
    private String nameEnc;
    private String ageEnc;
    private BigInteger ageHom;
    private String telEnc;
    private String idnumberEnc;
    private String incomeEnc;
    private BigInteger incomeHom;
    private String monthEnc;
    private BigInteger monthHom;
    private String outcomeEnc;
    private BigInteger outcomeHom;
    private String userid;
}
