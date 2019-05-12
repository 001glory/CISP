package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.AreaRepository;
import cn.judgchen.cisp.entity.Area;
import cn.judgchen.cisp.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;


    @Override
    public Page<Area> getListArea(int pageIndex, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex,pageSize, Sort.Direction.ASC,"create_time");
        Page<Area> areas = areaRepository.findAll(pageRequest);
        return areas;
    }
}
