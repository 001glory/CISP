package cn.judgchen.cisp.service;

import cn.judgchen.cisp.entity.Auths;
import org.springframework.data.domain.Page;

public interface AuthsService {

    Page<Auths> findAll(Integer page,Integer size);
}
