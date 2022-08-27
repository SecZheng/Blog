package com.sec.search.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.blog.entity.R;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SearchDegradeHandler implements BlockExceptionHandler {
    ObjectMapper om = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        om.writeValue(httpServletResponse.getWriter(), R.error("服务器繁忙，请稍后重试！"));
        httpServletResponse.getWriter().close();
    }
}
