package cn.edu.hit.cs.zhycryptdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEnc1A {

    private int id;
    private String orderid;
    private String customernameEnc;
    private String employeeid;
    private String productid;
    private String costEnc;
    private String priceEnc;
    private String numberEnc;
    private String userid;
}
