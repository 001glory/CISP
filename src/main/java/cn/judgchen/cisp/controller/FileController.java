package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.FileRepository;
import cn.judgchen.cisp.entity.File;
import cn.judgchen.cisp.service.FileService;
import cn.judgchen.cisp.service.UploadFileService;
import cn.judgchen.cisp.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UploadFileService uploadFileService;




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

    @LoggerManage(description = "上传微信文件")
    @PostMapping("/wx/upload")
    public ApiResponse uploadFile(MultipartFile multipartFile){
       String filePath =  FileUtil.fileSave(multipartFile);
        return ApiResponse.success(filePath);
    }

//    @PostMapping("/back")
//    @LoggerManage(description = "上传吗")
//    public ApiResponse back(HttpSession session, HttpServletRequest request){
//        System.out.println("执行了吗？" );
//        String msg = HttpServletRequestUtil.getString(request,"msg");
////        if (msg == null){
////            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
////        }
//        CommonsMultipartFile faultImage = null;
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        if (multipartResolver.isMultipart(request)){
//            MultipartHttpServletRequest httpServletRequest = (MultipartHttpServletRequest) request;
//            faultImage = (CommonsMultipartFile) httpServletRequest.getFile("file");
//        }
//        String url = fileService.uploadFile(faultImage);
//        System.out.println("url:"+url);
//        return ApiResponse.success(url);
//    }



}
