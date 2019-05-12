package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {

    File findFileByFileName(String fileName);

    @Transactional
    @Query(nativeQuery = true,value = "select *from sw_file where admin_id =:adminId and group_id =:groupId")
    List<File> findFilesByAdminIdAndGroupId(@Param("adminId") int adminId,@Param("groupId") int groupId);
}
