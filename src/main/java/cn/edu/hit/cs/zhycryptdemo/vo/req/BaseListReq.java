package cn.edu.hit.cs.zhycryptdemo.vo.req;

import lombok.ToString;

/**
 * 公共列表.
 * @author Li Changwei (lichangwei@jd.com)
 * @version $Id$
 * @since 0.1
 * Created on 2017/7/5
 */
@ToString
public class BaseListReq extends BaseReq {
    /**
     * 默认一页条数.
     */
    private static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 最大一页条数.
     */
    private static final int MAX_PAGE_SIZE = 100;
    /**
     * 最小一页条数.
     */
    private static final int MIN_PAGE_SIZE = 1;
    /**
     * 默认页号.
     */
    private static final int DEFAULT_PAGE_NUM = 1;
    /**
     * 页号.
     */
    private int pageNum;
    /**
     * 一页条数.
     */
    private int pageSize;

    public int getPageNum() {
        if (pageNum < BaseListReq.DEFAULT_PAGE_NUM) {
            pageNum = BaseListReq.DEFAULT_PAGE_NUM;
        }
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        if (pageSize < BaseListReq.MIN_PAGE_SIZE
            || pageSize > BaseListReq.MAX_PAGE_SIZE) {
            pageSize = BaseListReq.DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
