package com.yucong.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	/**
	 * 全局异常捕捉，可以返回字符串或者页面
	 * 
	 * @param request
	 * @param e
	 * @return
	 */

    @ResponseBody
	@ExceptionHandler(Exception.class)
	public String ff(HttpServletRequest request, Exception e) {

        ImmutableMap<String, String> map = ImmutableMap.of("code", "100000", "msg", e.getMessage());

        return JSON.toJSONString(map);
	}

}
