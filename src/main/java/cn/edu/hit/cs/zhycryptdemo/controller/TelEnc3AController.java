package cn.edu.hit.cs.zhycryptdemo.controller;

import cn.edu.hit.cs.zhycryptdemo.service.TelEnc3AService;
import cn.edu.hit.cs.zhycryptdemo.utils.ValidUtil;
import cn.edu.hit.cs.zhycryptdemo.vo.req.TelAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.TelListReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.res.JsonResponse;
import cn.edu.hit.cs.zhycryptdemo.vo.res.TelListResVo;
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
@RequestMapping("tel")
public class TelEnc3AController {

    @Autowired
    private TelEnc3AService telEnc3AService;

    @RequestMapping("list")
    public JsonResponse list(final @RequestBody @Validated TelListReqVo req, final BindingResult bindingResult) {
        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>("1", validRes);
            } else {
                List<TelListResVo> telListResVoList = this.telEnc3AService.listByPage(req);
                res = new JsonResponse(telListResVoList);
            }
        } finally {
            log.debug("tel.list : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }


    @RequestMapping("add")
    public JsonResponse insert(final @RequestBody @Validated TelAddReqVo req, final BindingResult bindingResult) {
        JsonResponse res = null;
        try {
            String validRes = ValidUtil.getErrMsg(bindingResult);
            if (validRes != null) {
                res = new JsonResponse<>("1", validRes);
            } else {
                int ic = this.telEnc3AService.insert(req);
                res = new JsonResponse(ic);
            }
        } finally {
            log.debug("tel.add : req [{}] , res [{}]", JSONObject.toJSONString(req), JSONObject.toJSONString(res));
        }
        return res;
    }
}
