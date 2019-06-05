package cn.judgchen.cisp.dao;


import cn.judgchen.cisp.entity.DLServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DLServerRepository extends JpaRepository<DLServer,Long> {

    List<DLServer> findDLServersByDlId(int dlId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update DLServer dl set dl.icon = :icon where dl.dlId = :dlId")
    int updateIcon(@Param("icon") String icon, @Param("dlId") int dlId);

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(COUNT(DISTINCT dl_id),0) from dl_server")
    int getTotalJdr();

    DLServer findById(int id);
}
