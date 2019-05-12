package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.CalouselsRepository;
import cn.judgchen.cisp.entity.Calouels;
import cn.judgchen.cisp.service.CalouselsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CalouselsServiceImpl implements CalouselsService {

    @Autowired
    private CalouselsRepository calouselsRepository;

    @Override
    public Page<Calouels> getALL(Integer page, Integer size) {
        if(size ==null ||size <10){
            size =10;
        }
        if (page ==null){
            page = 0;
        }
        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.DESC,"createTime");
        Page<Calouels> calouels = calouselsRepository.findAll(pageRequest);
        return calouels;
    }
}
