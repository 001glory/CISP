package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.RoleRepository;
import cn.judgchen.cisp.entity.Role;
import cn.judgchen.cisp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/get")
    @LoggerManage(description = "获取角色列表")
    public ApiResponse getList(int page, int size){

        Page<Role> roles = roleService.findAll(page,size);

        return ApiResponse.success(roles);
    }

    @PostMapping("/change")
    @LoggerManage(description = "改变用户的状态")
    public ApiResponse updateState(int id,String show){

        int state = 0;
        if(show.equals("disable")){
            state = 1;
        }
        if (show.equals("avaliable")){
            state = 0;
        }
        if (roleRepository.findRoleById(id) != null){
            roleRepository.updateState(state,id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/grant/del")
    @LoggerManage(description = "查看自己的权限之后删除")
    public ApiResponse deleteAuth(int id){
        int state = 1;
        if (roleRepository.findRoleById(id) != null){
            roleRepository.updateState(state,id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }

    }


    @PostMapping("/add")
    @LoggerManage(description = "添加新的角色")
    public ApiResponse add(Role role){

        if (roleRepository.findRoleByRoleName(role.getRoleName()) == null){
            role.setIsDelete(0);
            role.setCreateTime(LocalDateTime.now());
            roleRepository.save(role);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/update")
    @LoggerManage(description = "更新权限数据")
    public ApiResponse update(int id,String roleName,int state,int sort,String remarks){
        if(roleRepository.findRoleById(id) != null){
            roleRepository.updateData(id,roleName,state,remarks,sort);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }

    }

    @PostMapping("del")
    @LoggerManage(description = "删除角色")
    public ApiResponse delRole(int id){
        if (roleRepository.findRoleById(id) != null){
            roleRepository.deleteRoleById(id);

            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }
}
