package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Auths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AuthsRepository extends JpaRepository<Auths,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "select * from auths where is_delete =:isDelete")
    Page<Auths> findAuths(@Param("isDelete") int isDelete, Pageable pageRequest);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update  Auths set isDelete =:isDelete where id =:id")
    void update(@Param("isDelete") int isDelete,@Param("id") int id);

    Auths findAuthsById(int id);
}
