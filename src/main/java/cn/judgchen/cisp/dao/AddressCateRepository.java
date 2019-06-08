package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.AddressCate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressCateRepository extends JpaRepository<AddressCate,Long> {

    List<AddressCate> findAllByAId(int aId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AddressCate set isDelete=1 where id=:id")
    void deleteAddressInfo(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select * from address_cate where name=:name")
    AddressCate findAddressName(@Param("name") String name);

    @Transactional
    @Query(nativeQuery = true,value = "select * from address_cate WHERE id=:id ")
    AddressCate findAddress(@Param("id") Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AddressCate set name=:name,sort=:sort,isShow=:isShow where id=:id")
    void updateAddress(@Param("id") Integer id,@Param("name") String name,@Param("sort") Integer sort,@Param("isShow") Integer isShow);

    @Transactional
    @Query(nativeQuery = true,value = "select * from address_cate where is_delete=0 and is_show=1 and a_id=:aId")
    Page<AddressCate> getAreaList(@Param("aId") int aId, Pageable pageable);

}
