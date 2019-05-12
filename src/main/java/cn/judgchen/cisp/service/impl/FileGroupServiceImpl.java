package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.FileGroupRepository;
import cn.judgchen.cisp.entity.FileGroup;
import cn.judgchen.cisp.service.FileGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FileGroupServiceImpl implements FileGroupService {

    @Autowired
    private FileGroupRepository fileGroupRepository;

    @Override
    public Page<FileGroup> findAll(Integer page, Integer size) {

        if (page == null){
            page = 0;
        }
        if (size == null || size < 10){
            size =10;
        }
        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"createTime");
        Page<FileGroup> fileGroups = fileGroupRepository.findAll(pageRequest);
        return fileGroups;
    }
}
