package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.*;
import cn.judgchen.cisp.entity.AnalysisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analysis")
public class AnalysisDataController {

    @Autowired
    private ServerListRepository serverListRepository;

    @Autowired
    private WxUserRepository wxUserRepository;

    @Autowired
    private DLServerRepository dlServerRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostMapping("/getData")
    @Transactional
    @LoggerManage(description = "查询e_chart的数据")
    public ApiResponse getAnalysisData(){

        AnalysisData analysisData = new AnalysisData();

        analysisData.setWxuserTotal(wxUserRepository.getTotalUsers());
        analysisData.setUserPass(dlServerRepository.getTotalJdr());
        analysisData.setAreaTotal(areaRepository.getTotalArea());
        analysisData.setOrderTotal(serverListRepository.getTotalOrder());
        analysisData.setTurnover(serverListRepository.getTotalSale());
        analysisData.setTurnoverDaily(serverListRepository.getDailySale());
        analysisData.setTurnoverMonth(serverListRepository.getMonthSale());
        analysisData.setTurnoverYear(serverListRepository.getYearSale());
        analysisData.setDailyUser(wxUserRepository.getDailyUser());
        analysisData.setUserWating(userInfoRepository.getWaitUser());
        analysisData.setUserBack(userInfoRepository.getBackUser());

        return ApiResponse.success(analysisData);
    }


    @PostMapping("/agent/getData")
    @Transactional
    @LoggerManage(description = "查询代理的e_chart数据")
    public ApiResponse getAgentAnalysisData(int aId){

        AnalysisData analysisData = new AnalysisData();

        analysisData.setUserPass(userInfoRepository.getTotal());
        analysisData.setOrderTotal(serverListRepository.getAgentTotalOrder(aId));
        analysisData.setTurnover(serverListRepository.getAgentTotalSale(aId));
        analysisData.setTurnoverDaily(serverListRepository.getAgentDailySale(aId));
        analysisData.setTurnoverMonth(serverListRepository.getAgentMonthSale(aId));
        analysisData.setTurnoverYear(serverListRepository.getAgentYearSale(aId));
        analysisData.setUserWating(userInfoRepository.getAgentWaitUser(aId));
        analysisData.setUserBack(userInfoRepository.getAgentBackUser(aId));

        return ApiResponse.success(analysisData);
    }
}
