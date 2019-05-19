package cn.judgchen.cisp.dao;


import cn.judgchen.cisp.entity.JdOrder;
import cn.judgchen.cisp.entity.JdOrderDaily;
import cn.judgchen.cisp.entity.ServerList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ServerListRepository extends JpaRepository<ServerList,Long> {

    ServerList findByOrderNum(String orderNum);

    ServerList findById(int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ServerList set state =:state,isPay = :isPay where id = :id")
    int updateState(@Param("state") int state, @Param("isPay") int isPay, @Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT IFNULL(COUNT(order_num),0) FROM helplist")
    int getTotalOrder();


    @Transactional
    @Query(nativeQuery = true,value = "SELECT IFNULL(sum(total_fee),0.00) FROM helplist WHERE is_pay=1 and state in (1,2,3)")
    long getTotalSale();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(total_fee),0.00) from helplist where state in (1,2,3) and is_pay=1 and to_days(create_time) = to_days(now())")
    double getDailySale();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(total_fee),0.00) from helplist where state in (1,2,3) and is_pay=1 and DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )")
    double  getMonthSale();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(total_fee),0.00) from helplist where state in (1,2,3) and is_pay=1 and YEAR(create_time)=YEAR(NOW())")
    double getYearSale();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(total_fee),0.00) from helplist where state = 4 and is_pay=1")
    double getRefund();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(total_fee),0.00) from helplist where state = 4 and is_pay=1 and to_days(create_time) = to_days(now())")
    double getRefundDaily();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(total_fee),0.00) from helplist where state = 4 and is_pay=1 and DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )")
    double getRefundMonth();

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(sum(total_fee),0.00) from helplist where state = 4 and is_pay=1 and YEAR(create_time)=YEAR(NOW())")
    double getRefundYear();

    @Transactional
    @Query(nativeQuery = true,value = "SELECT helplist.*,wxuser.phone,wxuser.dphone,wxuser.avatar_url,wxuser.nick_name FROM helplist INNER JOIN wxuser ON helplist.wx_id = wxuser.id WHERE helplist.is_delete=0 AND helplist.state in (1,2,3) AND helplist.wx_id=:wxId")
    List<Map<String,Object>> findAllByWxId(@Param("wxId") int wxId);

    @Transactional
    @Query("select new cn.judgchen.cisp.entity.JdOrder(count(s.id),s.state) from ServerList as s where s.jdId=:jdId group by s.state")
    List<JdOrder> findAnalysisData(@Param("jdId") int jdId);

    @Transactional
    @Query("select new cn.judgchen.cisp.entity.JdOrderDaily(COUNT(s.id),s.state) from ServerList as s where s.jdId=:jdId and s.createTime like concat(:time,'%')   group by s.state")
    List<JdOrderDaily> finAnalysisDataDaliy(@Param("jdId") int jdId,@Param("time") String time);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT helplist.*,wxuser.phone,wxuser.dphone,wxuser.avatar_url,wxuser.nick_name FROM helplist INNER JOIN wxuser ON helplist.wx_id = wxuser.id WHERE helplist.is_delete=0 AND helplist.jd_id=:jdId")
    List<Map<String,Object>> getServerListByMy(@Param("jdId") int jdId);

    @Transactional
    @Query(nativeQuery = true,value = "select IFNULL(count(*),0) from helplist where jd_id =:jdId and state =3 and com_time like :conTime")
    int findMonthTotal(@Param("jdId") int jdId,@Param("conTime") String conTime);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT helplist.*,wxuser.phone,wxuser.dphone,wxuser.avatar_url,wxuser.nick_name FROM helplist LEFT JOIN wxuser ON helplist.wx_id = wxuser.id WHERE helplist.is_delete=0 AND helplist.id=:id")
    List<Map<String,Object>> findServerListById(@Param("id") int id);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT helplist.*,wxuser.phone,wxuser.dphone,wxuser.avatar_url,wxuser.nick_name FROM helplist INNER JOIN wxuser ON helplist.wx_id = wxuser.id WHERE helplist.is_delete=0 AND helplist.a_id=:aId")
    List<Map<String,Object>> getServerListByAndWxUser(@Param("aId") int aId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ServerList set jdId =:jdId,state=2,jdTime=:jdTime where id=:id")
    void updateJd(@Param("jdId") int jdId, @Param("id") int id,@Param("jdTime") LocalDateTime jdTime);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ServerList set isPay=0,state=4,cancelTime=:cancelTime where id=:id")
    void cancelOrder(@Param("id") int id,@Param("cancelTime") LocalDateTime cancelTime);

    @Transactional
    @Query(nativeQuery = true,value = "SELECT helplist.*,wxuser.phone,wxuser.dphone,wxuser.avatar_url,wxuser.nick_name FROM helplist INNER JOIN wxuser ON helplist.wx_id = wxuser.id WHERE helplist.is_delete=0 AND helplist.title like '%快递代取%' AND helplist.state IN (1,2,3,4) AND helplist.a_id=:aId")
    List<Map<String,Object>> getExpress(@Param("aId") int aId);


    @Transactional
    @Query(nativeQuery = true,value = "SELECT helplist.*,wxuser.phone,wxuser.dphone,wxuser.avatar_url,wxuser.nick_name FROM helplist INNER JOIN wxuser ON helplist.wx_id = wxuser.id WHERE helplist.is_delete=0 AND helplist.title like '%打印%' AND helplist.state IN (1,2,3,4) AND helplist.a_id=:aId")
    List<Map<String,Object>> getPrint(@Param("aId") int aId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ServerList set isPay=1,state=1,payTime=:payTime where id=:id")
    void pay(@Param("id") int id,@Param("payTime") LocalDateTime payTime);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ServerList set isPay=1,state=1,payTime=:payTime,totalFee=:totalFee where id=:id")
    void payment(@Param("id") int id,@Param("payTime") LocalDateTime payTime,@Param("totalFee") double totalFee);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ServerList set state=3,comTime=:comTime where id=:id")
    void confirm(@Param("id") int id, @Param("comTime") LocalDateTime comTime);
}
