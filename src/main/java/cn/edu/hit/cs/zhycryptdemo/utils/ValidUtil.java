package cn.edu.hit.cs.zhycryptdemo.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.BindingResult;

/**
 * 验证TMS返回信息
 */
public final class ValidUtil {
    /**
     * 构造.
     */
    private ValidUtil(){
    }

    /**
     * 取错误消息.
     * @param result 验证结果
     * @return
     */
    public static String getErrMsg(final BindingResult result) {
        String res = null;
        if (result.hasErrors()) {
            res = result.getAllErrors().get(0).getDefaultMessage();
        }
        return res;
    }
}
