package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.LogRepository;
import cn.judgchen.cisp.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogRepository logRepository;


    @PostMapping("/getList")
    @LoggerManage(description = "获取所有的日志列表")
    public ApiResponse getList(int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"create_time");
        Page<Log> logs = logRepository.findList(0,pageable);
        return ApiResponse.success(logs);
    }
}
