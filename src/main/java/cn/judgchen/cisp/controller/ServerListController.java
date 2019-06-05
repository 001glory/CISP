package cn.judgchen.cisp.controller;


import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.code.ErrorCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.CapitalTrendrepository;
import cn.judgchen.cisp.dao.ServerListRepository;
import cn.judgchen.cisp.dao.WalletsRepository;
import cn.judgchen.cisp.entity.*;
import cn.judgchen.cisp.service.ServerListService;
import cn.judgchen.cisp.utils.DifferenceTime;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private WalletsRepository walletsRepository;

    @PostMapping("/add")
    @LoggerManage(description = "增加一条服务记录")
    public ApiResponse addServer(ServerList serverList){

        LocalDateTime createTime = LocalDateTime.now();
        String orderNum = DifferenceTime.getOrder();
        serverList.setOrderNum(orderNum);
        serverList.setCreateTime(createTime);
//        System.out.println(orderNum);
        serverList.setState(0);
        serverList.setIsDelete(0);
        serverList.setIsPay(0);
        ServerList serverList1 = serverListService.selectByOrderNum(serverList.getOrderNum());
        if(serverList1==null){
            serverListService.addServerInfo(serverList);
            ServerList serverList2 = serverListService.selectByOrderNum(serverList.getOrderNum());
            return ApiResponse.success(serverList2);
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

    @PostMapping("/update/state")
    @LoggerManage(description = "模拟支付")
    public ApiResponse pay(int id){
        if (serverListRepository.findById(id) != null){
           LocalDateTime payTime = LocalDateTime.now();
            serverListRepository.pay(id,payTime);
            return ApiResponse.success(id);
        }else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }


    @PostMapping("/pay")
    @LoggerManage(description = "模拟支付")
    public ApiResponse payment(int id,int totalFee){
        if (serverListRepository.findById(id) != null){
            LocalDateTime payTime = LocalDateTime.now();
            serverListRepository.payment(id,payTime,totalFee);
            return ApiResponse.success(id);
        }else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
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
            LocalDateTime cancelTime = LocalDateTime.now();
            serverListRepository.cancelOrder(id,cancelTime);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(new ErrorCode(100,"取消失败"));
        }
    }

    @PostMapping("/confirm")
    @LoggerManage(description = "确认订单")
    @Transactional
    public ApiResponse confirm(int id){
        ServerList serverList = serverListRepository.findById(id);
        LocalDateTime comTime = LocalDateTime.now();
        if (serverList != null){
            if (capitalTrendrepository.findByHid(id) == null){

                CapitalTrend capitalTrend = new CapitalTrend();
                capitalTrend.setAId(serverList.getAId());
                capitalTrend.setAGet(serverList.getTotalFee()*0.2);
                capitalTrend.setCreateTime(comTime);
                capitalTrend.setPGet(serverList.getTotalFee()*0.4);
                capitalTrend.setUGet(serverList.getTotalFee()*0.4);
                capitalTrend.setUId(serverList.getJdId());
                capitalTrend.setHId(id);
                capitalTrendrepository.save(capitalTrend);
            }
            CapitalTrend capitalTrend = capitalTrendrepository.findByHid(id);
            capitalTrendrepository.updateByHid(capitalTrend.getUGet()+serverList.getTotalFee()*0.2,capitalTrend.getPGet()+serverList.getTotalFee()*0.4,capitalTrend.getAGet()+serverList.getTotalFee()*0.4,id);
            walletsRepository.updateWallets(capitalTrend.getUGet(),serverList.getJdId());
            serverListRepository.confirm(id,comTime);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/getExpress")
    @LoggerManage(description = "获取代取订单列表")
    public ApiResponse getExpress(int aId){
        List<Map<String,Object>> list = serverListRepository.getExpress(aId,"代取");
        return ApiResponse.success(list);
    }

    @PostMapping("/getPrint")
    @LoggerManage(description = "获取打印订单列表")
    public ApiResponse getPrint(int aId){
        List<Map<String,Object>> list = serverListRepository.getPrint(aId,"打印");
        return ApiResponse.success(list);
    }

    @PostMapping("delete")
    @LoggerManage(description = "删除订单")
    public ApiResponse deleteOrder(String []id){
        for (int i = 0; i <id.length ; i++) {
            serverListRepository.deleteServerList(Integer.parseInt(id[i]));
        }
        return ApiResponse.success();
    }

    @PostMapping("/like")
    @LoggerManage(description = "模糊搜索")
    public ApiResponse getListBylike(String orderNum,String phone,Integer aId){
        String name = "快递";
        if (orderNum != null){
            List<Map<String,Object>> list = serverListRepository.findServerListByOrderNum(name,orderNum,aId);
            return ApiResponse.success(list);
        } else if (phone != null){
            List<Map<String,Object>> list = serverListRepository.findServerListByPhone(name,phone,aId);
            return ApiResponse.success(list);
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/like1")
    @LoggerManage(description = "模糊搜索")
    public ApiResponse getListByLikePrint(String orderNum,String fileName,Integer aId){
        String name = "打印";
        if (orderNum != null){
            List<Map<String,Object>> list = serverListRepository.findServerListByOrderNum(name,orderNum,aId);
            return ApiResponse.success(list);
        } else if (fileName != null){
            List<Map<String,Object>> list = serverListRepository.findServerListByFileName(name,fileName,aId);
            return ApiResponse.success(list);
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/like2")
    @LoggerManage(description = "模糊搜索")
    public ApiResponse getAllListByLike(String orderNum,String phone,Integer aId){
        if (orderNum != null){
            List<Map<String,Object>> list = serverListRepository.findALLByOrderNum(orderNum,aId);
            return ApiResponse.success(list);
        } else if (phone != null){
            List<Map<String,Object>> list = serverListRepository.findAllByPhone(phone,aId);
            return ApiResponse.success(list);
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }
}
