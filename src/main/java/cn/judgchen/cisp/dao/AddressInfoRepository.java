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


}
