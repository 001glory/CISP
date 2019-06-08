package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findUserInfoByWxId(int wxId);

    UserInfo findById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserInfo set name=:name,cardNum=:cardNum,cert=:cert,stuCard=:stuCard,aId=:aId where wxId=:wxId")
    void applyJd(@Param("wxId") int wxId,@Param("name") String name,@Param("cardNum") String cardNum,@Param("cert") String cert,@Param("stuCard") String stuCard,@Param("aId") int aId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserInfo set state=0,name=:name,cardNum=:cardNum,cert=:cert,stuCard=:stuCard,aId=:aId,msg='审核中' where wxId=:wxId")
    void updateCard(@Param("wxId") int wxId,@Param("name") String name,@Param("cardNum") String cardNum,@Param("cert") String cert,@Param("stuCard") String stuCard,@Param("aId") int aId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserInfo set state=1,msg='审核通过' where wxId=:wxId")
    void updateState(@Param("wxId") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserInfo set state=2,msg=:msg where wxId=:wxId")
    void updateStateMsg(@Param("wxId") int id,@Param("msg") String msg);

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(COUNT(DISTINCT id),0) from userinfo where state=1")
    Integer getTotal();
    

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(COUNT(id),0) from userinfo where state=0 and card_num is not null")
    Integer getWaitUser();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(COUNT(id),0) from userinfo where state=2 and card_num is not null")
    Integer getBackUser();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(COUNT(id),0) from userinfo where state=0 and card_num is not null and a_id=:aId")
    Integer getAgentWaitUser(@Param("aId") int aId);

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(COUNT(id),0) from userinfo where state=2 and card_num is not null and a_id=:aId")
    Integer getAgentBackUser(int aId);
}
