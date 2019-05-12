package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.AuthsRepository;
import cn.judgchen.cisp.dao.RoleAuthRepository;
import cn.judgchen.cisp.entity.AuthDto;
import cn.judgchen.cisp.entity.Auths;
import cn.judgchen.cisp.service.AuthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthsController {

    @Autowired
    private AuthsRepository authsRepository;

    @Autowired
    private RoleAuthRepository roleAuthRepository;

    @Autowired
    private AuthsService authsService;


    @PostMapping("/getList")
    @LoggerManage(description = "获取所有的权限信息")
    public ApiResponse getList(int page,int size){

        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        Page<Auths> auths = authsRepository.findAuths(0,pageable);

        return ApiResponse.success(auths);
    }

    @PostMapping("/del")
    @LoggerManage(description = "删除权限路径")
    public ApiResponse delete(int id){
        if(authsRepository.findAuthsById(id) != null){
            authsRepository.update(1,id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/grant/get")
    @LoggerManage(description = "获取相应的权限路径")
    public ApiResponse getList(int page,int size,int roleId){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        Page<Auths> auths = authsRepository.findAuths(0,pageable);

        return ApiResponse.success(auths);
    }

}
