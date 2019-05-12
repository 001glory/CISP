package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findUserInfoByWxId(int wxId);
}
