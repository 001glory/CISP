package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.FileRepository;
import cn.judgchen.cisp.entity.File;
import cn.judgchen.cisp.service.FileService;
import cn.judgchen.cisp.utils.ImageUtil;
import cn.judgchen.cisp.utils.ImgHolderUtil;
import cn.judgchen.cisp.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<File> getFiles(int adminId, int groupId) {

        List<File> files = fileRepository.findFilesByAdminIdAndGroupId(adminId,groupId);
        return files;
    }

    @Override
    public String uploadFile(CommonsMultipartFile faultImage) {

        File file = new File();

        try {
            ImgHolderUtil imgHolder = new ImgHolderUtil(faultImage.getOriginalFilename(),faultImage.getInputStream());
            if (imgHolder.getImage() != null){
                return FileServiceImpl.addFaultImg(file,imgHolder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String addFaultImg(File file, ImgHolderUtil imgHolder) {
        //获取图片目录的相对值路径
        String dest = PathUtil.getImgBasePath();
        String ImgAddr = ImageUtil.generateThumbnail(imgHolder,dest);
        return ImgAddr;
    }
}
