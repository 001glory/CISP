package cn.judgchen.cisp.service;

import cn.judgchen.cisp.entity.Area;
import org.springframework.data.domain.Page;

public interface AreaService {

    Page<Area> getListArea(int pageIndex, int pageSize);
}
