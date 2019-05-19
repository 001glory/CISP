package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.CashRepository;
import cn.judgchen.cisp.dao.WalletsRepository;
import cn.judgchen.cisp.entity.Cash;
import cn.judgchen.cisp.entity.Wallets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wallets")
public class WalletsController {

    @Autowired
    private WalletsRepository walletsRepository;

    @Autowired
    private CashRepository cashRepository;


    @PostMapping("/get")
    @LoggerManage(description = "获取钱包的信息")
    public ApiResponse getWalletsInfo(int uid){

        if (walletsRepository.findByUid(uid) != null){

            Wallets wallets = walletsRepository.getWalletsInfo(uid);

            return ApiResponse.success(wallets);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/get2")
    @LoggerManage(description = "获取每个订单的收支情况")
    public ApiResponse getAll(int aId){
        List<Map<String,Object>> list = walletsRepository.findAllList(aId);
        return ApiResponse.success(list);
    }


    @PostMapping("/get/uid")
    @LoggerManage(description = "获取钱包的信息")
    public ApiResponse getInfo(int type,int uid){
        if (walletsRepository.findByUid(uid) != null){
            Wallets wallets = walletsRepository.getInfo(type,uid);
            return ApiResponse.success(wallets);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/cash")
    @LoggerManage(description = "提现")
    public ApiResponse update(int uid,double cashFree){
        Wallets wallets = walletsRepository.findByUid(uid);
        double money = cashFree+wallets.getCash();
        if (wallets != null) {
            walletsRepository.updstate(uid,wallets.getIncomeTotal(),money);
            Cash cash = new Cash();
            cash.setIsDelete(0);
            cash.setCreateTime(LocalDateTime.now());
            cash.setState(1);
            cash.setType(1);
            cash.setCashFree(cashFree);
            cash.setUid(uid);
            cash.setMsg("提现成功！");
            cashRepository.save(cash);
            return ApiResponse.success(cash.getMsg());
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }



    }

}
