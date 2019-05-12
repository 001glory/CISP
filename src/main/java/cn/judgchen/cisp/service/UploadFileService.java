package cn.judgchen.cisp.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    String getUploadFilePath(MultipartFile file);
}
