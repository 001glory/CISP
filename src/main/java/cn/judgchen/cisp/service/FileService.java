package cn.judgchen.cisp.service;


import cn.judgchen.cisp.entity.File;

import java.util.List;

public interface FileService {

    List<File> getFiles(int adminId,int groupId);

}
