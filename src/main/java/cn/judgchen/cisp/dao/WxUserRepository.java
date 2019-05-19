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
    @Query(nativeQuery = true,value = "select * from wxuser where auth =1")
    Page<WxUser> findJDList(Pageable pageable);

    @Transactional
    @Query(nativeQuery = true,value = "select * from wxuser")
    Page<WxUser> findList(Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update WxUser set defaultAddress =:defaultAddress where id =:id")
    void updateDefault(@Param("id") int id,@Param("defaultAddress") int defaultAddress);


    @Transactional
    @Query(nativeQuery = true,value = "select wxuser.*,userinfo.name,userinfo.card_num,userinfo.cert,userinfo.stu_card,userinfo.id s_id,userinfo.state,userinfo.msg from wxuser inner join userinfo on wxuser.id = userinfo.wx_id")
    List<Map<String,Object>> getWxUserInfo();

}
