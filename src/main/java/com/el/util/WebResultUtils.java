package com.el.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * web 返回值整理
 * @author Danfeng
 * @since 2018/6/29
 */
public class WebResultUtils {

    private static final Logger log = LoggerFactory.getLogger(WebResultUtils.class);
    private static final String OP_RESULT_OK = "OK";
    private static final String OP_RESULT_NG = "NG";


    private static ResponseEntity.BodyBuilder opBuilder(OpResult result) {
        return ResponseEntity.status(HttpStatus.OK).header("el-result-code", result == null ? OP_RESULT_OK : OP_RESULT_NG);
    }

    private static ResponseEntity.BodyBuilder okBuilder() {
        return ResponseEntity.status(HttpStatus.OK).header("el-result-code", OP_RESULT_OK);
    }

    public static ResponseEntity.BodyBuilder ngBuilder() {
        return ResponseEntity.status(HttpStatus.OK).header("el-result-code", OP_RESULT_NG);
    }
    private static ResponseEntity.BodyBuilder badReqBuilder(OpResult result) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("el-result-code", result == null ? OP_RESULT_NG : result.getCode());
    }

    public static ResponseEntity toResponseEntity(OpResult result) {
        return opBuilder(result).build();
    }

    public static ResponseEntity toResponseEntity() {
        return okBuilder().build();
    }

    /**
     * 语义有误，当前请求无法被服务器理解 | 请求参数有误
     * @param result 自定义code 与 前端code 一一对应
     * @return ResponseEntity status 404
     */
    public static ResponseEntity tobadReqEntity(OpResult result) {
        return badReqBuilder(result).build();
    }

    /**
     * 前端错误码对接
     * @param result
     * @return
     */
    public static ResponseEntity toNgReqEntity(OpResult result) {
        return opBuilder(result).build();
    }
    /**
     * 校验错误
     * @param body
     * @return
     */
    public static ResponseEntity toNgValidateEntity(Object body) {
        return ngBuilder().body(body);
    }

    /**
     * 前端错误码对接
     * @return
     */
    public static ResponseEntity toOkReqEntity(Object body) {
        return okBuilder().body(body);
    }

}
