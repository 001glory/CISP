package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.CalouselsRepository;
import cn.judgchen.cisp.entity.Calouels;
import cn.judgchen.cisp.service.CalouselsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class CalouselsServiceImpl implements CalouselsService {

    @Autowired
    private CalouselsRepository calouselsRepository;

    @Override
    public Page<Calouels> getALL(Calouels calouels, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page,size);
        Specification<Calouels> spec = new Specification<Calouels>() {
            @Override
            public Predicate toPredicate(Root<Calouels> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (!ObjectUtils.isEmpty(calouels.getCompany())){
                    list.add(cb.like(root.get("company").as(String.class),"%"+calouels.getCompany()+"%"));
                }
                if (!ObjectUtils.isEmpty((calouels.getSort()))){
                    list.add(cb.equal(root.get("sort").as(Integer.class),calouels.getSort()));
                }
                Predicate []p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return calouselsRepository.findAll(spec,pageable);
    }
}
