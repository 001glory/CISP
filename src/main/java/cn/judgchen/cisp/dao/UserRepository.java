package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsername(String username);

    void deleteByUsername(String username);

    User findUserByAId(int aId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User set pwd=:newPwd where username=:username")
    int updatePasswordBy(@Param("username") String username, @Param("newPwd") String newPwd);

    User findUserByPkId(int pkId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User set openEmer =:openEmer,emerTitle =:emerTitle,emerContent =:emerContent where pkId =:pkId")
    void updateEmer(@Param("pkId") int pkId,@Param("openEmer") int openEmer,@Param("emerTitle") String emerTitle,@Param("emerContent")String emerContent);

    @Transactional
    @Query(nativeQuery = true,value = "select * from y_user where dtype =2")
    Page<User> findDLUser(Pageable pageable);
}
