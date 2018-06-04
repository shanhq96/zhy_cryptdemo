package cn.edu.hit.cs.zhycryptdemo.vo.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 组织统一返回前端格式.
 *
 * @author Li Changwei (lichangwei@jd.com)
 * @version $Id$
 * @since 0.1
 * Created on 2017/7/5
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public final class JsonResponse<T> {
    /**
     * 返回编码.
     */
    private String errorCode;
    /**
     * 返回错误消息.
     */
    private String errorMsg;

    /**
     * 返回数据.
     */
    private T data;

    public JsonResponse(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public JsonResponse(T data) {
        this.errorCode = "0";
        this.data = data;
    }
}
