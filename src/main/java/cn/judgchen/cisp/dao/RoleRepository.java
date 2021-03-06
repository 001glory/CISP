package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role,Long> {


    Role findRoleById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Role set state =:state where id =:id")
    void updateState(@Param("state") int state,@Param("id") int id);


    Role findRoleByRoleName(String roleName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "update roles set role_name =:roleName,state =:state,remarks =:remarks,sort =:sort where id =:id")
    int updateData(@Param("id") int id,@Param("roleName") String roleName,@Param("state") int state,@Param("remarks") String remarks,@Param("sort") int sort);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Role set isDelete = 1 where id =:id")
    void deleteRoleById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Role set isDelete = 1 where id =:id")
    void deleteRole(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select * from roles where is_delete=0")
    Page<Role> findAllRoles(Pageable pageable);

    @Transactional
    @Query(nativeQuery = true,value = "select * from roles where is_delete=0 and role_name like CONCAT('%',:name,'%')")
    Page<Role> findAllRoleByName(@Param("name") String name, Pageable pageable);
}
