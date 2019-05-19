package cn.judgchen.cisp.service;


import cn.judgchen.cisp.entity.File;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface FileService {

    List<File> getFiles(int adminId,int groupId);

    String uploadFile(CommonsMultipartFile faultImage);

}
