package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Calouels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalouselsRepository extends JpaRepository<Calouels,Long> {

    @Query(nativeQuery = true,value = "select *from calousels where is_show=1 and is_delete=0 and a_id =:aId")
    List<Calouels> findCalouels(@Param("aId") int aId);
}
