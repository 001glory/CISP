package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.entity.FileGroup;
import cn.judgchen.cisp.service.FileGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ptfile")
public class FileGroupController {

    @Autowired
    private FileGroupService fileGroupService;

    @PostMapping("/getGroup")
    @LoggerManage(description = "获取全部分组信息")
    public ApiResponse getFileGroup(int page,int size){
        Page<FileGroup> fileGroups = fileGroupService.findAll(page,size);
        return ApiResponse.success(fileGroups);
}
}
