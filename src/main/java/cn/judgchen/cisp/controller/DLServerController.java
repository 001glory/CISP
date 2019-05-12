package cn.judgchen.cisp.controller;


import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.entity.DLServer;
import cn.judgchen.cisp.service.DLServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/server")
public class DLServerController {

    @Autowired
    private DLServerService dlServerService;

    @PostMapping("/add")
    @LoggerManage(description = "添加服务")
    public ApiResponse addDLServer(DLServer dlServer){
        LocalDateTime createTime = LocalDateTime.now();
        dlServer.setCreateTime(createTime);
        dlServerService.addDLServer(dlServer);
        return ApiResponse.success(dlServer);
    }

    @PostMapping("/getList")
    @LoggerManage(description = "获取服务列表")
    public ApiResponse getListByUid(int uid){

        List<DLServer> dlServerList = dlServerService.selectDLServer(uid);
        if (dlServerList!=null){
            return ApiResponse.success(dlServerList);
        }
        return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
    }

    @PostMapping("/update")
    @LoggerManage(description = "根据代理id更新头像")
    @Transactional
    public ApiResponse updateDLIcon(String icon,int dlId){
        dlServerService.updateDLIcon(icon,dlId);
        return ApiResponse.success();
    }
}
