package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.AuthCate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AuthCateRepository extends JpaRepository<AuthCate,Long> {

    AuthCate findAuthCateById(int id);

    @Transactional
    @Query(nativeQuery = true,value = "select * from auth_cate where  is_delete =:isDelete")
    Page<AuthCate> findAll(@Param("isDelete") int isDelete, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AuthCate set isDelete =1 where id =:id")
    void update(@Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AuthCate set isShow =:isShow where id =:id")
    void updateShow(@Param("isShow") int isShow,@Param("id") int id);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AuthCate set cateName =:cateName,isShow =:isShow,sort =:sort,remarks =:remarks where id =:id")
    void updateAll(int id, String cateName, int isShow, int sort, String remarks);
}
