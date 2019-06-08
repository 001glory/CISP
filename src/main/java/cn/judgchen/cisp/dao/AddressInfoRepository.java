package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.AddressInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AddressInfoRepository extends JpaRepository<AddressInfo,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "select * from address_info where cate_id =:cateId and is_delete =0 and a_id =:aId")
    Page<AddressInfo> findList(@Param("cateId") int cateId,@Param("aId") int aId, Pageable pageable);


    @Transactional
    @Query(nativeQuery = true,value = "select * from address_info where a_id=:aId and is_delete=0")
    Page<AddressInfo> getAreaList(@Param("aId") int aId, Pageable pageable);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AddressInfo set isDelete=1 where id=:id")
    void deleteAddressInfo(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select * from address_info where id=:id")
    AddressInfo findAddress(@Param("id") Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AddressInfo set cateId=:cateId,name=:name,subName=:subName,sort=:sort where id=:id")
    void updateAddress(@Param("id") Integer id,@Param("cateId") Integer cateId,@Param("name") String name,@Param("subName") String subName,@Param("sort") Integer sort);

    @Transactional
    @Query(nativeQuery = true,value = "select * from address_info where cate_id=:cateId")
    AddressInfo findAddressByCateId(@Param("cateId") Integer cateId);
}
