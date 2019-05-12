package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.CalouselsRepository;
import cn.judgchen.cisp.entity.Calouels;
import cn.judgchen.cisp.service.CalouselsService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/calousels")
public class CalouselsController {

    @Autowired
    private CalouselsRepository calouselsRepository;

    @Autowired
    private CalouselsService calouselsService;

    @PostMapping("/get")
    @LoggerManage(description = "微信根据aid获取本校区的轮播图")
    public ApiResponse getList(int aId){
        List<Calouels> calouels = calouselsRepository.findCalouels(aId);
        return ApiResponse.success(calouels);
    }

    @PostMapping("/getAll")
    @LoggerManage(description = "管理员获取所有的轮播图")
    public ApiResponse getAll(int page,int size){

        Page<Calouels> calouels = calouselsService.getALL(page,size);
        return ApiResponse.success(calouels);
    }

    @PostMapping("/add")
    @LoggerManage(description = "新增广告")
    public ApiResponse add(Calouels calouels){
        int isDelete = 0;
        LocalDateTime localDateTime = LocalDateTime.now();
        calouels.setIsDelete(isDelete);
        calouels.setCreateTime(localDateTime);
        calouselsRepository.save(calouels);
        return ApiResponse.success();
    }
}
