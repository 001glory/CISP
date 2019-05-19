package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findUserInfoByWxId(int wxId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserInfo set name=:name,cardNum=:cardNum,cert=:cert,stuCard=:stuCard,aId=:aId where wxId=:wxId")
    void applyJd(@Param("wxId") int wxId,@Param("name") String name,@Param("cardNum") String cardNum,@Param("cert") String cert,@Param("stuCard") String stuCard,@Param("aId") int aId);
}
