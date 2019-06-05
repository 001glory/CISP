package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.WxUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface WxUserRepository extends JpaRepository<WxUser,Long> {

    @Transactional
    @Query(nativeQuery = true,value = "select count(id) from  wxuser")
    int getTotalUsers();

    WxUser findWxUserByOpenid(String openid);


    @Transactional
    @Query(nativeQuery = true,value = "select *from wxuser where  nick_name =?")
    WxUser findByWxUser(@Param("nickName") String nickName);


    WxUser findById(int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set phone =:phone,dphone =:dphone where id =:id")
    void updatePhone(@Param("phone")String phone,@Param("dphone") String dphone,@Param("id") int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set nickName =:nickName,phone =:phone,gender =:gender,city =:city,province =:province,avatarUrl =:avatarUrl where id =:id")
    void updateWxUserInfo(@Param("nickName") String nickName,@Param("phone") String phone,@Param("gender") int gender,@Param("city") String city,@Param("province") String province,@Param("avatarUrl") String avatarUrl,@Param("id") int id);


    @Transactional
    @Query(nativeQuery = true,value = "select * from wxuser where auth!=0")
    Page<WxUser> findJDList(Pageable pageable);

    @Transactional
    @Query(nativeQuery = true,value = "select * from wxuser")
    Page<WxUser> findList(Pageable pageable);

    @Transactional
    @Query(nativeQuery = true,value = "select * from wxuser where id=:id")
    Page<WxUser> findList1(@Param("id") int id,Pageable pageable);


    @Transactional
    @Query(nativeQuery = true,value = "select * from wxuser where nick_name like CONCAT('%',:name,'%')")
    Page<WxUser> findList2(@Param("name") String name,Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update WxUser set defaultAddress =:defaultAddress where id =:id")
    void updateDefault(@Param("id") int id,@Param("defaultAddress") int defaultAddress);


    @Transactional
    @Query(nativeQuery = true,value = "select wxuser.*,userinfo.name,userinfo.card_num,userinfo.cert,userinfo.stu_card,userinfo.id s_id,userinfo.state,userinfo.msg from wxuser inner join userinfo on wxuser.id = userinfo.wx_id where userinfo.name is not null ")
    List<Map<String,Object>> getWxUserInfo();

    @Transactional
    @Query(nativeQuery = true,value = "select wxuser.*,userinfo.name,userinfo.card_num,userinfo.cert,userinfo.stu_card,userinfo.id s_id,userinfo.state,userinfo.msg from wxuser inner join userinfo on wxuser.id = userinfo.wx_id where userinfo.name is not null and userinfo.wx_id=:id ")
    List<Map<String,Object>> getWxUserInfo1(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select wxuser.*,userinfo.name,userinfo.card_num,userinfo.cert,userinfo.stu_card,userinfo.id s_id,userinfo.state,userinfo.msg from wxuser inner join userinfo on wxuser.id = userinfo.wx_id where userinfo.name is not null and userinfo.name like CONCAT('%',:name,'%')")
    List<Map<String,Object>> getWxUserInfo2(@Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set nickName=:nickName,phone=:phone where id =:id")
    void updateInfo(@Param("nickName") String nickName, @Param("phone") String phone,@Param("id") int id);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set auth=1 where id =:id")
    void updateAuthState(int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set isDelete=1 where id =:id")
    void updateSate(@Param("id") int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set isDelete=0 where id =:id")
    void updateSates(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select * from wxuser where phone like CONCAT('%',:phone,'%')")
    Page<WxUser> findList3(@Param("phone") String phone, Pageable pageable);

    @Transactional
    @Query(nativeQuery = true,value = "select * from wxuser where gender like CONCAT('%',:gender,'%')")
    Page<WxUser> findList4(@Param("gender") Integer gender, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set auth =1 where id =:id")
    void updateJdUserState(@Param("id") int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set auth =2 where id =:id")
    void updateJdUserStates(@Param("id") int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update WxUser set auth =0 where id =:id")
    void updateAuthStates(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(count(id),0) from wxuser where is_delete=0 and to_days(create_time) = to_days(now())")
    Integer getDailyUser();
}
