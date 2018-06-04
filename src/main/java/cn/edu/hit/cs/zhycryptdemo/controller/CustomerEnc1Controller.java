package cn.edu.hit.cs.zhycryptdemo.controller;

import cn.edu.hit.cs.zhycryptdemo.service.CustomerEnc1Service;
import cn.edu.hit.cs.zhycryptdemo.utils.ValidUtil;
import cn.edu.hit.cs.zhycryptdemo.vo.req.CustomerAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.CustomerListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.CustomerResVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("customer")
public class CustomerEnc1Controller {

    @Autowired
    private CustomerEnc1Service customerEnc1Service;

    @RequestMapping("list")
    public JsonResponse list(final @RequestBody @Validated CustomerListReqVo req, final BindingResult bindingResult) {
        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>(validRes);
            } else {
                List<CustomerResVo> customerResVo = this.customerEnc1Service.listByPage();
                res = new JsonResponse(customerResVo);
            }
        } finally {
            log.debug("customer.list : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }


    @RequestMapping("add")
    public JsonResponse insert(final @RequestBody @Validated CustomerAddReqVo req, final BindingResult bindingResult) {
        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>(validRes);
            } else {
                int ic = this.customerEnc1Service.insert(req);
                res = new JsonResponse(ic);
            }
        } finally {
            log.debug("customer.add : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }
}
