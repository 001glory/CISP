package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.RoleRepository;
import cn.judgchen.cisp.entity.Role;
import cn.judgchen.cisp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private  RoleRepository roleRepository;

    @Override
    public Page<Role> findAll(Integer page, Integer size) {

        if (page == null){
            page = 0;
        }
        if (size == null || size < 10){
            size =10;
        }
        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.ASC,"createTime");

        Page<Role> roles = roleRepository.findAll(pageRequest);
        return roles;
    }
}
