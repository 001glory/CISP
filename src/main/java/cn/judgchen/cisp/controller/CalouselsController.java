package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.CalouselsRepository;
import cn.judgchen.cisp.entity.Calouels;
import cn.judgchen.cisp.service.CalouselsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"create_time");
        Page<Calouels> calouels = calouselsRepository.getAll(pageable);
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


    @PostMapping("/updateShow")
    @LoggerManage(description = "修改轮播图是否显示")
    public ApiResponse updateShow(int id,String show){

        int isShow = 0;

        if (show.equals("unshow")){
            isShow = 0;
        } else {
            isShow = 1;
        }

        if (calouselsRepository.findById(id) != null){
            calouselsRepository.updateShow(isShow,id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/delete")
    @LoggerManage(description = "删除轮播图")
    public ApiResponse deleteCalouels(int id){
        if (calouselsRepository.findById(id) != null){

            calouselsRepository.updateDelete(id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }
}
