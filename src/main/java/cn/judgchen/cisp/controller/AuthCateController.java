package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.AuthCateRepository;
import cn.judgchen.cisp.entity.AuthCate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/cate")
public class AuthCateController {

    @Autowired
    private AuthCateRepository authCateRepository;

    @PostMapping("/add")
    @LoggerManage(description = "添加一条权限类目记录")
    public ApiResponse add(AuthCate authCate){
        authCate.setIsDelete(0);
        authCate.setCreateTime(LocalDateTime.now());
        authCateRepository.save(authCate);
        return ApiResponse.success();
    }

    @PostMapping("/update")
    @LoggerManage(description = "添加一条权限类目记录")
    public ApiResponse updateAuthCate(int id, String cateName,int isShow,int sort,String remarks){
        if(authCateRepository.findAuthCateById(id) != null){
            authCateRepository.updateAll(id,cateName,isShow,sort,remarks);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }


    @PostMapping("/get")
    @LoggerManage(description = "获取所有的权限类目")
    public ApiResponse getList(int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        Page<AuthCate> authCates =  authCateRepository.findAll(0,pageable);
        return ApiResponse.success(authCates);
    }

    @PostMapping("/del")
    @LoggerManage(description = "删除权限类目记录")
    public ApiResponse deleteCate(int id){
        if (authCateRepository.findAuthCateById(id) != null){
            authCateRepository.update(id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/change")
    @LoggerManage(description = "改变权限类目的状态")
    public ApiResponse updateShow(int isShow,int id){

        if (authCateRepository.findAuthCateById(id) != null){
            authCateRepository.updateShow(isShow,id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }

    }

    @PostMapping("/like")
    @LoggerManage(description = "模糊查询")
    public ApiResponse getByName(String cateName,int page,int size){

        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        Page<AuthCate> authCates =  authCateRepository.findByName(cateName,pageable);
        return ApiResponse.success(authCates);
    }
}
