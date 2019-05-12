package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.AddressCateRepository;
import cn.judgchen.cisp.entity.AddressCate;
import cn.judgchen.cisp.service.AddressCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AddressCateServiceImpl implements AddressCateService {

    @Autowired
    private AddressCateRepository addressCateRepository;

    @Override
    public Page<AddressCate> getList(Integer page, Integer size) {

        if (page == null){
            page = 0;
        }
        if (size <10 || size == null){
            size = 10;
        }
        PageRequest pageRequest  = PageRequest.of(page,size, Sort.Direction.DESC,"createTime");

        Page<AddressCate> addressCates = addressCateRepository.findAll(pageRequest);

        return addressCates;
    }
}
