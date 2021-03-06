package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.AreaRepository;
import cn.judgchen.cisp.entity.Area;
import cn.judgchen.cisp.service.AreaService;
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
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;


    @Override
    public Page<Area> getAll(Area area, int page, int size) {

        Pageable pageable = new PageRequest(page,size);
        Specification<Area> spec = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (!ObjectUtils.isEmpty(area.getName())){
                    list.add(cb.like(root.get("name").as(String.class),"%"+area.getName()+"%"));
                }
                if (!ObjectUtils.isEmpty((area.getPkId()))){
                    list.add(cb.equal(root.get("pkId").as(Integer.class),area.getPkId()));
                }
                Predicate []p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return areaRepository.findAll(spec,pageable);
    }
}
