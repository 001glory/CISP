package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.FileRepository;
import cn.judgchen.cisp.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author luoliang
 * @date 2018/2/12
 **/
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
//    @Value("${prop.upload-folder}")
//    private String UPLOAD_FOLDER;
    private Logger logger = LoggerFactory.getLogger(UploadController.class);


    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/singlefiles")
    public Object singleFileUpload(MultipartFile file) {
//        System.out.println(UPLOAD_FOLDER);
        System.out.println(file.getSize());
        System.out.println(file.isEmpty());
//        logger.debug("传入的文件参数：{}", JSON.toJSONString(file, true));
        if (!file.isEmpty()) {
            try {
                //图片命名
                String newCompanyImageName = "newPIC";
                String newCompanyImagepath = "C:\\data\\images\\" + newCompanyImageName;
                File newFile = new File(newCompanyImagepath);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(newFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                System.out.println(file.isEmpty());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("       图片上传失败1         \n");
                return "图片上传失败1！";

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("       图片上传失败2        \n");
                return "图片上传失败2！";
            }
        }
        System.out.println("       图片上传失败3        \n");
        return "图片上传失败3！";
    }

    @RequestMapping("/singlefile")
    @LoggerManage(description = "轮播图上传至服务器")
    public ApiResponse save(@RequestParam("files") MultipartFile files) throws IOException {
//        //获取项目编译之后的文件路径，一般是“项目路径/target/classes”
//        String Path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")+"static/img/")).replaceAll("file:/", "").replaceAll("%20", " ").trim();
//        if(Path.indexOf(":") != 1){
//            Path = File.separator + Path;
//        }
//        String fileUrl="http://localhost:8080";
//        //遍历文件
//        if(files != null && files.length>0){
//            for(MultipartFile item : files){
//                String fileName = item.getOriginalFilename();//获取文件名
//                String filePath = Path + "img/";//自定义上传路径
//                File file = new File(filePath,fileName);
//                if(!file.exists()){//判断文件夹是否存在，如果不存在则创建
//                    if(!file.getParentFile().exists()){
//                        file.getParentFile().mkdirs();
//                    }
//                    file.createNewFile();
//                }
//                item.transferTo(file);//上传文件
//
//
//                logger.info("上传的文件路径"+file);
//            }
//        }
        if (fileRepository.findFileByFileName(files.getName()) == null) {
            String fileUrl = uploadFileService.getUploadFilePath(files);

            return ApiResponse.success(fileUrl);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_HAS_EXISTED);
        }
    }
}