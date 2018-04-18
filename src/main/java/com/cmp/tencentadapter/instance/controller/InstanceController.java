package com.cmp.tencentadapter.instance.controller;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.cmp.tencentadapter.common.BaseController;
import com.cmp.tencentadapter.common.JsonUtil;
import com.cmp.tencentadapter.instance.model.req.ReqCloseInstance;
import com.cmp.tencentadapter.instance.model.req.ReqStartInstance;
import com.cmp.tencentadapter.instance.service.InstanceService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletionStage;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("")
public class InstanceController extends BaseController {

    @Autowired
    private InstanceService instanceService;

    /**
     * 查询主机列表
     *
     * @param request  http请求 http请求
     * @param response http响应 http响应
     * @return 主机列表
     */
    @RequestMapping("/instances")
    @ResponseBody
    public CompletionStage<JsonNode> describeInstances(
            final HttpServletRequest request,
            final HttpServletResponse response) {
        return getCloudEntity(request)
                .thenApply(instanceService::describeInstances)
                .thenApply(x -> okFormat(OK.value(), x, response))
                .exceptionally(e -> badFormat(e, response));
    }

    /**
     * 关闭主机
     *
     * @param request  http请求
     * @param response http响应
     * @return 关闭主机
     * @throws IOException 异常
     */
    @RequestMapping("/instances/close")
    @ResponseBody
    public CompletionStage<JsonNode> closeInstance(
            final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String body = IOUtils.read(reader);
        ReqCloseInstance reqCloseInstance = JsonUtil.stringToObject(body, ReqCloseInstance.class);
        return getCloudEntity(request)
                .thenAccept(cloud -> instanceService.closeInstance(cloud, reqCloseInstance))
                .thenApply(x -> okFormat(OK.value(), null, response))
                .exceptionally(e -> badFormat(e, response));
    }

    /**
     * 启动主机
     *
     * @param request  http请求
     * @param response http响应
     * @return 操作结果
     * @throws IOException 异常
     */
    @RequestMapping("/instances/start")
    @ResponseBody
    public CompletionStage<JsonNode> startInstance(
            final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String body = IOUtils.read(reader);
        ReqStartInstance reqStartInstance = JsonUtil.stringToObject(body, ReqStartInstance.class);
        return getCloudEntity(request)
                .thenAccept(cloud -> instanceService.startInstance(cloud, reqStartInstance))
                .thenApply(x -> okFormat(OK.value(), null, response))
                .exceptionally(e -> badFormat(e, response));
    }
}
