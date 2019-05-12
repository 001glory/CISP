package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Auths;
import cn.judgchen.cisp.entity.RoleAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RoleAuthRepository extends JpaRepository<RoleAuth,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "select auths.id,auths.auth_name,auths.auth_url from role_auth INNER JOIN auths ON role_auth.auth_id = auths.id WHERE role_auth.auth_id = auths.id and role_auth.role_id =:roleId")
//    Page<Object> getAuths(@Param("roleId") int roleId, Pageable pageable);
    List<Object> getAuths(@Param("roleId") int roleId);

}
