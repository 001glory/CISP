package cn.judgchen.cisp.service;

import cn.judgchen.cisp.entity.AddressCate;
import org.springframework.data.domain.Page;

public interface AddressCateService {

    Page<AddressCate> getList(Integer page,Integer size);
}
