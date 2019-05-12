package cn.judgchen.cisp.controller;


import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.code.ErrorCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.CapitalTrendrepository;
import cn.judgchen.cisp.dao.ServerListRepository;
import cn.judgchen.cisp.entity.AnalysisCount;
import cn.judgchen.cisp.entity.JdOrder;
import cn.judgchen.cisp.entity.JdOrderDaily;
import cn.judgchen.cisp.entity.ServerList;
import cn.judgchen.cisp.service.ServerListService;
import cn.judgchen.cisp.utils.DifferenceTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/index")
public class ServerListController {


    @Autowired
    private ServerListRepository serverListRepository;

    @Autowired
    private ServerListService serverListService;

    @Autowired
    private CapitalTrendrepository capitalTrendrepository;

    @PostMapping("/add")
    @LoggerManage(description = "增加一条服务记录")
    public ApiResponse addServer(ServerList serverList){

        LocalDateTime createTime = LocalDateTime.now();
        String orderNum = DifferenceTime.getOrder();
        serverList.setOrderNum(orderNum);
        serverList.setCreateTime(createTime);
//        System.out.println(orderNum);
        serverList.setState(1);
        serverList.setIsDelete(0);
        serverList.setIsPay(0);
        ServerList serverList1 = serverListService.selectByOrderNum(serverList.getOrderNum());
        if(serverList1==null){
            serverListService.addServerInfo(serverList);
            return ApiResponse.success(serverList);
        }else {
            return ApiResponse.fail(ConstanCode.ORDER_ALREADY_EXISTS);
        }
    }

    @PostMapping("/update")
    @LoggerManage(description = "更新订单状态")
    @Transactional
    public ApiResponse updateState(int state,int isPay,int id){
        long startTime = System.currentTimeMillis();

        serverListService.upateStateById(state,isPay,id);
        long endTime  = System.currentTimeMillis();
        System.out.println("修盖耗时：#############"+(endTime-startTime));
        return ApiResponse.success();
    }

    @PostMapping("/get")
    @LoggerManage(description = "获取帮助列表")
    public ApiResponse getList(int aId){
        List<Map<String,Object>> list = serverListRepository.getServerListByAndWxUser(aId);
        return ApiResponse.success(list);
    }

    @PostMapping("/get2")
    @LoggerManage(description = "获取我发出的帮助信息")
    public ApiResponse getList2(int wxId){

        List<Map<String,Object>> serverLists = serverListRepository.findAllByWxId(wxId);
        return ApiResponse.success(serverLists);

    }

    @PostMapping("/get3")
    @LoggerManage(description = "获取我帮助的")
    public ApiResponse getMyHelp(int jdId){
        List<Map<String,Object>> serverLists = serverListRepository.getServerListByMy(jdId);

        return ApiResponse.success(serverLists);
    }


    @PostMapping("/get/order/state")
    @LoggerManage(description = "获取分析的参数")
    public ApiResponse getAnalysisData(String createTime,String sbtype){
        if (sbtype.equals("month")){

            AnalysisCount analysisCount = new AnalysisCount();
            analysisCount.setTime(createTime);
            analysisCount.setTotal(4);
            analysisCount.setState("s0");

            AnalysisCount analysisCount1 = new AnalysisCount();
            analysisCount1.setTime(createTime);
            analysisCount1.setTotal(5);
            analysisCount1.setState("s1");

            AnalysisCount analysisCount2 = new AnalysisCount();
            analysisCount2.setTime(createTime);
            analysisCount2.setTotal(6);
            analysisCount2.setState("s2");

            AnalysisCount analysisCount3 = new AnalysisCount();
            analysisCount3.setTime(createTime);
            analysisCount3.setTotal(6);
            analysisCount3.setState("s3");

            AnalysisCount analysisCount4 = new AnalysisCount();
            analysisCount4.setTime(createTime);
            analysisCount4.setTotal(6);
            analysisCount4.setState("s4");

            ArrayList<AnalysisCount> analysisCounts = new ArrayList<>();
            analysisCounts.add(analysisCount);
            analysisCounts.add(analysisCount1);
            analysisCounts.add(analysisCount2);
            analysisCounts.add(analysisCount3);
            analysisCounts.add(analysisCount4);
            return ApiResponse.success(analysisCounts);
        } else {
            AnalysisCount analysisCount5 = new AnalysisCount();

            analysisCount5.setTime(createTime);
            analysisCount5.setTotal(3);
            analysisCount5.setState("s0");
            ArrayList<AnalysisCount> analysisCountts = new ArrayList<>();
            analysisCountts.add(analysisCount5);
            return ApiResponse.success(analysisCountts);
        }

    }

    @PostMapping("/analysis/wx")
    @LoggerManage(description = "获取接单数据分析")
    public ApiResponse getAnalysisData(int jdId){

      List<JdOrder> jdOrders =  serverListRepository.findAnalysisData(jdId);

        return ApiResponse.success(jdOrders);
    }
    @PostMapping("/analysis/wx1")
    @LoggerManage(description = "获取接单数据分析")
    public ApiResponse getAnalysisData1(int jdId){
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String time = sdf.format(date);

        List<JdOrderDaily> jdOrderDailies =  serverListRepository.finAnalysisDataDaliy(jdId,time);

        return ApiResponse.success(jdOrderDailies);
    }



    @PostMapping("/get/sm")
    @LoggerManage(description = "获取微信数据分析")
    public ApiResponse getOrderData(int wxId){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        String conTime = localTime+"%";
        double sum =capitalTrendrepository.getTotalUGet(wxId,conTime);
        int count = serverListRepository.findMonthTotal(wxId,conTime);
        Map<String,Object> map = new HashMap();
        map.put("count",count);
        map.put("sum",sum);
        return ApiResponse.success(map);
    }

    @PostMapping("/get/detail")
    @LoggerManage(description = "获取帮助的详情")
    public ApiResponse getDetail(int id){

        List<Map<String,Object>> serverList = serverListRepository.findServerListById(id);
        return ApiResponse.success(serverList);

    }

    @PostMapping("/jd")
    @LoggerManage(description = "接单")
    public ApiResponse updateJd(int jdId,int id){
        ServerList serverList = serverListRepository.findById(id);
        if (serverList !=null && serverList.getJdId() == null){
            LocalDateTime localDateTime =LocalDateTime.now();
            serverListRepository.updateJd(jdId,id,localDateTime);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(new ErrorCode(100,"此单有人接了！"));
        }
    }

    @PostMapping("/cancel")
    @LoggerManage(description = "取消订单")
    public ApiResponse cancelOrder(int id){
        if (serverListRepository.findById(id) != null){
            serverListRepository.cancelOrder(id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(new ErrorCode(100,"取消失败"));
        }


    }

    @PostMapping("/getExpress")
    @LoggerManage(description = "获取帮助列表")
    public ApiResponse getExpress(int aId){
        List<Map<String,Object>> list = serverListRepository.getExpress(aId);
        return ApiResponse.success(list);
    }

    @PostMapping("/getPrint")
    @LoggerManage(description = "获取帮助列表")
    public ApiResponse getPrint(int aId){
        List<Map<String,Object>> list = serverListRepository.getPrint(aId);
        return ApiResponse.success(list);
    }

}
