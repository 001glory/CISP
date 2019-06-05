package cn.judgchen.cisp.service;

import cn.judgchen.cisp.entity.Log;
import org.springframework.data.domain.Page;

public interface LogService {

    Page<Log> getLogs(Log log,Integer page,Integer size);
}
