package cn.judgchen.cisp.service;

import cn.judgchen.cisp.entity.FileGroup;
import org.springframework.data.domain.Page;

public interface FileGroupService {

    Page<FileGroup> findAll(Integer page,Integer size);
}
