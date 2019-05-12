package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.AuthsRepository;
import cn.judgchen.cisp.entity.Auths;
import cn.judgchen.cisp.service.AuthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthsServiceImpl implements AuthsService {

    @Autowired
    private AuthsRepository authsRepository;

    @Override
    public Page<Auths> findAll(Integer page, Integer size) {
        if (page == null){
            page = 0;
        }
        if (size == null || size < 10){
            size =10;
        }
        PageRequest pageRequest =  PageRequest.of(page,size, Sort.Direction.ASC,"id");
        Page<Auths> auths = authsRepository.findAll(pageRequest);
        return auths;
    }
}
