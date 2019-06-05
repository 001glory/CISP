package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LogRepository extends JpaRepository<Log,Long>, JpaSpecificationExecutor<Log> {

    @Transactional
    @Query(nativeQuery = true,value = "select * from y_logs where is_delete =:isDelete")
    Page<Log> findList(@Param("isDelete") int isDelete, Pageable pageable);


    @Transactional
    @Query(nativeQuery = true,value = "select * from y_logs where create_time like CONCAT('%',:createTime,'%')")
    Page<Log> findListByTime(@Param("createTime") String createTime, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Log set isDelete=1 where id=:id")
    void deleteLog(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select * from y_logs where uid:uid")
    Page<Log> findListById(@Param("uid") Integer uid, Pageable pageable);
}
