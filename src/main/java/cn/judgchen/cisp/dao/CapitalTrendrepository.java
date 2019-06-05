package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.CapitalTrend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CapitalTrendrepository extends JpaRepository<CapitalTrend,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(u_get),0) from capital_trend where u_id =:uId and create_time like :comTime")
    double getTotalUGet(@Param("uId") int uId,@Param("comTime") String comTime);

    @Transactional
    @Query(nativeQuery = true,value = "select * from capital_trend where h_id=:hId")
    CapitalTrend findByHid(@Param("hId") Integer hId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update CapitalTrend set uGet=:uGet,pGet=:pGet,aGet=:aGet where hId=:id")
    void updateByHid(@Param("uGet") double uGet,@Param("pGet") double pGet,@Param("aGet") double aGet,@Param("id") Integer id);



}
