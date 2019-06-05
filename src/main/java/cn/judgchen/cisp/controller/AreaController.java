package cn.judgchen.cisp.controller;


import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.AreaRepository;
import cn.judgchen.cisp.entity.Area;
import cn.judgchen.cisp.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaRepository areaRepository;


    @PostMapping("/getList")
    @LoggerManage(description = "后台获取地址信息")
    public ApiResponse getArea(int page,int size){

        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"create_time");

        Page<Area> areas = areaRepository.getAreaList(pageable);

        return ApiResponse.success(areas);
    }

    @PostMapping("/get")
    @LoggerManage(description = "小程序获取地址信息")
    public ApiResponse getList(){
        List<Area> areas = areaRepository.findAll();
        return ApiResponse.success(areas);
    }


    @PostMapping("/add")
    @LoggerManage(description = "添加学校" )
    public ApiResponse addArea(Area area){
        if (areaRepository.findAreaByName(area.getName()) == null){
            area.setCreateTime(LocalDateTime.now());
            area.setIsDelete(0);
            areaRepository.save(area);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.ORDER_ALREADY_EXISTS);
        }
    }

    @PostMapping("/update")
    @LoggerManage(description = "修改学校信息")
    public ApiResponse updateArea(int pkId,String name,int sort){
        if (areaRepository.findAreaByPkId(pkId) != null){
            areaRepository.updateArea(pkId,name,sort);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/delete")
    @LoggerManage(description = "删除校园地区")
    public ApiResponse deleteArea(String []id){
        for (int i = 0; i <id.length ; i++) {
            areaRepository.deleteAreaById(Integer.parseInt(id[i]));
        }
        return ApiResponse.success();
    }

    @PostMapping("/like")
    @LoggerManage(description = "模糊查询")
    public ApiResponse getAreaList(int page, int size, Area area){
        Page<Area> areas = areaService.getAll(area,page,size);
        return ApiResponse.success(areas);
    }
}
