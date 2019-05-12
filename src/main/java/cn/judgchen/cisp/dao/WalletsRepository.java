package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Wallets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface WalletsRepository extends JpaRepository<Wallets,Long> {

    Wallets findByUid(int uid);

    @Transactional
    @Query(nativeQuery = true,value = "select * from wallets where type =2 and uid =:uid")
    Wallets getWalletsInfo(@Param("uid") int uid);


    @Transactional
    @Query(nativeQuery = true,value = "select capital_trend.*,helplist.order_num,helplist.total_fee from capital_trend inner join helplist on capital_trend.h_id = helplist.id where capital_trend.a_id =:aId")
    List<Map<String,Object>> findAllList(@Param("aId") int aId);
}
