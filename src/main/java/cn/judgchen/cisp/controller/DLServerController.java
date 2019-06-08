package cn.judgchen.cisp.controller;


import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.DLServerRepository;
import cn.judgchen.cisp.dao.UserRepository;
import cn.judgchen.cisp.entity.DLServer;
import cn.judgchen.cisp.entity.User;
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

    @Autowired
    private DLServerRepository dlServerRepository;

    @Autowired
    private UserRepository userRepository;

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

        User user = userRepository.findUserByPkId(uid);
        List<DLServer> dlServerList = dlServerService.selectDLServer(user.getAId());
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


    @PostMapping("/get/detail/id")
    @LoggerManage(description = "根据服务id获取服务详情")
    @Transactional
    public ApiResponse getServerById(int id){
        DLServer dlServer = dlServerRepository.findById(id);
        if (dlServer != null){
            return ApiResponse.success(dlServer);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }

    }
}
