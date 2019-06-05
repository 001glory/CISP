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

        return ApiResponse.success(analysisData);
    }


}
