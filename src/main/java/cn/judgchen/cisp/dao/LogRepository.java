package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LogRepository extends JpaRepository<Log,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "select * from y_logs where is_delete =:isDelete")
    Page<Log> findList(@Param("isDelete") int isDelete, Pageable pageable);
}
