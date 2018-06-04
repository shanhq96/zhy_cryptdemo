package cn.edu.hit.cs.zhycryptdemo.controller;

import cn.edu.hit.cs.zhycryptdemo.service.CustomerEnc1Service;
import cn.edu.hit.cs.zhycryptdemo.service.EmployeeEnc2Service;
import cn.edu.hit.cs.zhycryptdemo.utils.ValidUtil;
import cn.edu.hit.cs.zhycryptdemo.vo.req.CustomerAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.CustomerListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.CustomerListResVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.EmployeeListResVo;
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
@RequestMapping("employee")
public class EmployeeEnc2Controller {

    @Autowired
    private EmployeeEnc2Service employeeEnc2Service;

    @RequestMapping("list")
    public JsonResponse list(final @RequestBody @Validated EmployeeListReqVo req, final BindingResult bindingResult) {
        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>(validRes);
            } else {
                List<EmployeeListResVo> employeeListResVoList = this.employeeEnc2Service.listByPage(req);
                res = new JsonResponse(employeeListResVoList);
            }
        } finally {
            log.debug("employee.list : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }


    @RequestMapping("add")
    public JsonResponse insert(final @RequestBody @Validated EmployeeAddReqVo req, final BindingResult bindingResult) {
        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>(validRes);
            } else {
                int ic = this.employeeEnc2Service.insert(req);
                res = new JsonResponse(ic);
            }
        } finally {
            log.debug("employee.add : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }
}
