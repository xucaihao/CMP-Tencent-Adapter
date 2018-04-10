package com.cmp.tencentadapter.sys;

import com.cmp.tencentadapter.common.BaseController;
import com.cmp.tencentadapter.region.service.RegionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletionStage;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("")
public class SysController extends BaseController {

    @Autowired
    private RegionService regionService;

    /**
     * 用户权限认证
     *
     * @param request  http请求
     * @param response http响应
     * @return 结果
     */
    @RequestMapping("/authenticate")
    @ResponseBody
    public CompletionStage<JsonNode> authenticate(
            final HttpServletRequest request,
            final HttpServletResponse response) {
        return getCloudEntity(request)
                .thenApply(cloud -> regionService.describeRegions(cloud))
                .thenApply(x -> okFormat(OK.value(), x, response))
                .exceptionally(e -> badFormat(e, response));
    }

}
