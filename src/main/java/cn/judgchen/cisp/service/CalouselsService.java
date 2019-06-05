package cn.judgchen.cisp.service;

import cn.judgchen.cisp.entity.Calouels;
import org.springframework.data.domain.Page;

public interface CalouselsService {
    Page<Calouels> getALL(Calouels calouels,Integer page,Integer size);
}
