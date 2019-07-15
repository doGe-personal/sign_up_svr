package com.el.util;

/**
 * POST 操作返回结果
 *
 * @author neo.pan
 * @since 17/3/7
 */
public interface OpResult {

    /**
     * 放置结果代码的 HTTP HEADER 属性
     */
    String HTTP_HEADER_ATTR = "el-result-code";

    /**
     * OK - 表示处理成功
     */
    OpResult OK = () -> "OK";

    /**
     * NG - 表示处理失败、但具体原因要看`Response Body`
     */
    OpResult NG = () -> "NG";

    /**
     * @return 结果代码
     */
    String getCode();

    /**
     * @return 结果代码的解释（属于前端的事情，这里的内容仅作参考）
     */
    default String getDesc() {
        return getCode();
    }

}
