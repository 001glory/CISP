package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.AddressUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressUserRepository extends JpaRepository<AddressUser,Long> {

    List<AddressUser> findAddressUsersByWxId(int wxId);

    AddressUser findAddressUserById(int id);

    @Transactional
    @Query(nativeQuery = true,value = "select * from address_user where is_delete = 0 and wx_id =:wxId")
    Page<AddressUser> findList(@Param("wxId") int wxId, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AddressUser set address =:address,detail =:detail where id =:id")
    void updateAddressInfo(@Param("address") String address,@Param("detail") String detail,@Param("id") int id);
}
