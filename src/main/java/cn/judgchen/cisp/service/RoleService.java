package cn.judgchen.cisp.service;

import cn.judgchen.cisp.entity.Role;
import org.springframework.data.domain.Page;

public interface RoleService {

    Page<Role> findAll(Integer page,Integer size);
}
