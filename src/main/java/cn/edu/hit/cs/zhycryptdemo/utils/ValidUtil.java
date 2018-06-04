package cn.edu.hit.cs.zhycryptdemo.utils;

import org.springframework.validation.BindingResult;

public final class ValidUtil {
    private ValidUtil() {
    }

    public static String getErrMsg(final BindingResult result) {
        String res = null;
        if (result.hasErrors()) {
            res = result.getAllErrors().get(0).getDefaultMessage();
        }
        return res;
    }
}
