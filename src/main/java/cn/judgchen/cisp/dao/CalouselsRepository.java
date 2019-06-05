package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Calouels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CalouselsRepository extends JpaRepository<Calouels,Long>, JpaSpecificationExecutor<Calouels> {

    @Query(nativeQuery = true,value = "select *from calousels where is_show=1 and is_delete=0 and a_id =:aId")
    List<Calouels> findCalouels(@Param("aId") int aId);
    
    @Transactional
    @Query(nativeQuery = true,value = "select * from calousels where is_delete=0")
    Page<Calouels> getAll(Pageable pageable);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Calouels set isShow =:isShow where id =:id")
    void updateShow(@Param("isShow") int isShow,@Param("id") int id);


    Calouels findById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Calouels set isDelete = 1 where id =:id")
    void updateDelete(@Param("id") int id);
}
