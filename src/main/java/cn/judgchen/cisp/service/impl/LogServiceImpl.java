package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.LogRepository;
import cn.judgchen.cisp.entity.Log;
import cn.judgchen.cisp.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
public class LogServiceImpl implements LogService {


    @Autowired
    private LogRepository logRepository;

    @Override
    public Page<Log> getLogs(Log log, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page,size);
        Specification<Log> spec = new Specification<Log>() {
            List<Predicate> list = new ArrayList<Predicate>();
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                if (!ObjectUtils.isEmpty(log.getUid())){
                    list.add(cb.equal(root.get("uid").as(Integer.class),log.getUid()));
                }
                if (!ObjectUtils.isEmpty(log.getCreateTime())){
                    list.add(cb.like(root.get("create_time").as(String.class),"%"+log.getCreateTime()+"%"));
                }
                Predicate []p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return logRepository.findAll(spec,pageable);
    }
}
