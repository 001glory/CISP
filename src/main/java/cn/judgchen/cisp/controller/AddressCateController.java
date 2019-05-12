package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.AddressCateRepository;
import cn.judgchen.cisp.dao.AddressInfoRepository;
import cn.judgchen.cisp.dao.AddressUserRepository;
import cn.judgchen.cisp.dao.WxUserRepository;
import cn.judgchen.cisp.entity.AddressCate;
import cn.judgchen.cisp.entity.AddressInfo;
import cn.judgchen.cisp.entity.AddressUser;
import cn.judgchen.cisp.service.AddressCateService;
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
@RequestMapping("/address")
public class AddressCateController {

    @Autowired
    private AddressCateRepository addressCateRepository;

    @Autowired
    private AddressCateService addressCateService;

    @Autowired
    private AddressUserRepository addressUserRepository;

    @Autowired
    private AddressInfoRepository addressInfoRepository;

    @Autowired
    private WxUserRepository wxUserRepository;


    @PostMapping()
    @LoggerManage(description = "获取地址寝室宿舍楼栋")
    public ApiResponse getAll(int page,int size){

        Page<AddressCate> addressCates = addressCateService.getList(page,size);

        return ApiResponse.success(addressCates);
    }

    @PostMapping("/cate/get")
    @LoggerManage(description = "根据aId获取楼栋情况")
    public ApiResponse findAllByAid(int aId){

        List<AddressCate> addressCates = addressCateRepository.findAllByAId(aId);
        return ApiResponse.success(addressCates);
    }

    @PostMapping("/user/get")
    @LoggerManage(description = "该用户的所有地址信息")
    public ApiResponse findList(int page,int size,int wxId){

        if (addressUserRepository.findAddressUsersByWxId(wxId) != null){
            Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"create_time");

            Page<AddressUser> addressUsers = addressUserRepository.findList(wxId,pageable);

            return ApiResponse.success(addressUsers);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }


    @PostMapping("/get")
    @LoggerManage(description = "获取下拉地址信息")
    public ApiResponse getInfoList(int page,int size,int aId,int cateId){

        Pageable pageable = new PageRequest(page,size, Sort.Direction.DESC,"id");

        Page<AddressInfo> addressInfos = addressInfoRepository.findList(cateId,aId,pageable);

        return ApiResponse.success(addressInfos);
    }

    @PostMapping("/add")
    @LoggerManage(description = "新增用户地址")
    public ApiResponse addAddress(String address,String detail,int wxId){

        AddressUser addressUser = new AddressUser();

        addressUser.setAddress(address);
        addressUser.setCreateTime(LocalDateTime.now());
        addressUser.setIdDelete(0);
        addressUser.setDetail(detail);
        addressUser.setWxId(wxId);
        addressUserRepository.save(addressUser);

        return ApiResponse.success();
    }

    @PostMapping("/update")
    @LoggerManage(description = "更新用户的地址")
    public ApiResponse updateAddress(String address,String detail,int id){
        if (addressUserRepository.findAddressUserById(id) != null){
            addressUserRepository.updateAddressInfo(address,detail,id);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }

    }

    @PostMapping("/update/def/address")
    @LoggerManage(description = "更新为默认地址")
    public ApiResponse updateDefault(int id,int defaultAddress){
        if (wxUserRepository.findById(id) != null){
            wxUserRepository.updateDefault(id,defaultAddress);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

}
