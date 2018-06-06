package cn.edu.hit.cs.zhycryptdemo.controller;

import cn.edu.hit.cs.zhycryptdemo.service.EmployeeEnc2Service;
import cn.edu.hit.cs.zhycryptdemo.utils.ValidUtil;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeIncreaseSalaryReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeListReqVo;
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
                res = new JsonResponse<>("1", validRes);
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
    public JsonResponse add(final @RequestBody @Validated EmployeeAddReqVo req, final BindingResult bindingResult) {
        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>("1", validRes);
            } else {
                int ic = this.employeeEnc2Service.insert(req);
                res = new JsonResponse(ic);
            }
        } finally {
            log.debug("employee.add : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }


    @RequestMapping("salaryIncrease")
    public JsonResponse increaseSalary(final @RequestBody @Validated EmployeeIncreaseSalaryReqVo req, final BindingResult bindingResult) {

        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>("1", validRes);
            } else {
                boolean flag = this.employeeEnc2Service.increaseSalary(req);
                if (flag) {
                    res = new JsonResponse();
                } else {
                    res = new JsonResponse("1", "涨薪失败");
                }
            }
        } finally {
            log.debug("employee.salaryIncrease : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }
}
