package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.FileRepository;
import cn.judgchen.cisp.entity.File;
import cn.judgchen.cisp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileRepository fileRepository;


    @Autowired
    private FileService fileService;

    @PostMapping("/get")
    @LoggerManage(description = "获取自己的文件列表")
    public ApiResponse getFiles(int adminId, int groupId){

        List<File> files = fileService.getFiles(adminId,groupId);
        return ApiResponse.success(files);
    }


    @PostMapping("/upload")
    @LoggerManage(description = "保存轮播图信息")
    public ApiResponse add(File file){
        if (fileRepository.findFileByFileName(file.getFileName()) ==null){
            file.setIsDelete(0);
            file.setCreateTime(LocalDateTime.now());
            fileRepository.save(file);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_HAS_EXISTED);
        }
    }
}
