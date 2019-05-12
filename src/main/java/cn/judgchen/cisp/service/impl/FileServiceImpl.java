package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.FileRepository;
import cn.judgchen.cisp.entity.File;
import cn.judgchen.cisp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
