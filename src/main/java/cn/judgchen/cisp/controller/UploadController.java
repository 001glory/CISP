package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.FileRepository;
import cn.judgchen.cisp.service.UploadFileService;
import cn.judgchen.cisp.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Random;

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


    //文件存储路径
    @Value("${img.local.path}")
    private String imgLocalPath;

    //文件网络访问路径
    @Value("${img.host}")
    private String imgHost;

    //图片存放根目录下的子目录
    @Value("${file.sonPath}")
    private String SON_PATH;


    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private FileRepository fileRepository;


    @RequestMapping("/file")
    @LoggerManage(description = "轮播图上传至服务器")
    public ApiResponse save(@RequestParam("files") MultipartFile files) throws IOException {

        if (fileRepository.findFileByFileName(files.getName()) == null) {
            String fileUrl = uploadFileService.getUploadFilePath(files);

            return ApiResponse.success(fileUrl);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_HAS_EXISTED);
        }
    }

    @RequestMapping("/back")
    @LoggerManage(description = "证件照片")
    public ApiResponse save(HttpServletRequest request, @RequestParam(required=true,value="file")MultipartFile file) throws IOException {

        if(!file.isEmpty()) {
            logger.info("成功获取照片");
            String fileName = file.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            logger.info("图片初始名称为：" + fileName + " 类型为：" + type);
            if (type != null) {
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
//                    String realPath = request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    String suffix = file.getOriginalFilename();
                    String prefix = suffix.substring(suffix.lastIndexOf(".")+1);
                    //为防止文件重名被覆盖，文件名取名为：当前日期 + 1-1000内随机数
                    Random random = new Random();
                    Integer randomFileName = random.nextInt(1000);
                    String fileName1 = DateUtils.getDateSequence()+ randomFileName +"." +  prefix;
                    // 设置存放图片文件的路径
                    path = imgLocalPath+ fileName1;
                    logger.info("存放图片文件的路径:" + path);
                    file.transferTo(new File(path));

                    logger.info("文件成功上传到指定目录下");
//                    String url = imgHost+SON_PATH+fileName1;
                    //服务器地址
                    String url = imgHost+"/usr"+SON_PATH+fileName1;
                    logger.info(url);
                    return ApiResponse.success(url);
                }else {
                    logger.info("不是我们想要的文件类型,请按要求重新上传");
                }
            }else {
                logger.info("文件类型为空");
            }
        }else {
            logger.info("没有找到相对应的文件");
        }

        return ApiResponse.success();

    }
}