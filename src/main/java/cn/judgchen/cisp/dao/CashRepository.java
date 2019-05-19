package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<Cash,Long> {

    Cash findByUid(int uid);

    Cash findCashByCashFree(double cashFres);

}
