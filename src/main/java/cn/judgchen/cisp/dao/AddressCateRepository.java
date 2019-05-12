package cn.judgchen.cisp.dao;

import cn.judgchen.cisp.entity.AddressCate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressCateRepository extends JpaRepository<AddressCate,Long> {

    List<AddressCate> findAllByAId(int aId);

}
