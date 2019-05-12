package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AreaRepository extends JpaRepository<Area,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "SELECT IFNULL(COUNT(DISTINCT pk_id),0) FROM area where is_delete = 0")
    int getTotalArea();

    @Transactional
    @Query(nativeQuery = true,value = "select * from  area")
    Page<Area> getAreaList(Pageable pageable);

    Area findAreaByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Area set name =:name,sort =:sort where pkId =:pkId")
    void updateArea(@Param("pkId") int pkId,@Param("name") String name, @Param("sort") int sort);

    Area findAreaByPkId(int pkId);
}
