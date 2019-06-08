package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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


    @Transactional
    @Query(nativeQuery = true,value = "select y_user.*,area.name from y_user inner join area on y_user.a_id=area.pk_id where y_user.is_delete=0 and y_user.pk_id !=:uid and y_user.dtype !=1")
    List<Map<String,Object>> getDlServer(@Param("uid") int uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "update y_user set user_state='DISABLE' where pk_id=:pkId")
    void updateUserSate(@Param("pkId") int pkId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "update y_user set user_state='AVAILABLE' where pk_id=:pkId")
    void updateUserSates(@Param("pkId")int pkId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( "update User set userState='DISABLE',isDelete=1 where pk_id=:id")
    void deleteUser(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select y_user.*,area.name from y_user inner join area on y_user.a_id=area.pk_id where y_user.is_delete=0 and y_user.pk_id !=:uid and y_user.dtype !=1 and y_user.username like CONCAT('%',:username,'%')")
    List<Map<String, Object>> getDlServerByName(@Param("uid") int uid,@Param("username") String username);

    @Transactional
    @Query(nativeQuery = true,value = "select y_user.*,area.name from y_user inner join area on y_user.a_id=area.pk_id where y_user.is_delete=0  and y_user.pk_id !=:uid and y_user.dtype !=1 and y_user.phone like CONCAT('%',:phone,'%')")
    List<Map<String, Object>> getDlServerByPhone(@Param("uid") int uid,@Param("phone") String phone);
}
