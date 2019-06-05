package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.LogRepository;
import cn.judgchen.cisp.entity.Log;
import cn.judgchen.cisp.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogService logService;


    @PostMapping("/getList")
    @LoggerManage(description = "获取所有的日志列表")
    public ApiResponse getList(int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"create_time");
        Page<Log> logs = logRepository.findList(0,pageable);
        return ApiResponse.success(logs);
    }

    @PostMapping("/like")
    @LoggerManage(description = "获取所有的日志列表")
    public ApiResponse getLogs(String createTime,Integer uid,int page,int size){
        if (createTime != null){
            Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"create_time");
            Page<Log> logs = logRepository.findListByTime(createTime,pageable);
            return ApiResponse.success(logs);
        } else if (uid != null){
            Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"create_time");
            Page<Log> logs = logRepository.findListById(uid,pageable);
            return ApiResponse.success(logs);
        }
        return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
    }

    @PostMapping("/delete")
    @LoggerManage(description = "删除日志")
    public ApiResponse delete(String []id){
        for (int i = 0; i <id.length ; i++) {
            logRepository.deleteLog(Integer.parseInt(id[i]));
        }
        return ApiResponse.success();
    }
}
