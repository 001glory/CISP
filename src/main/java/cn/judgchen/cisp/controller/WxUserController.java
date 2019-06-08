package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.code.ErrorCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.UserInfoRepository;
import cn.judgchen.cisp.dao.WalletsRepository;
import cn.judgchen.cisp.dao.WxUserRepository;
import cn.judgchen.cisp.entity.*;
import cn.judgchen.cisp.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx")
public class WxUserController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxUserRepository wxUserRepository;


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private WalletsRepository walletsRepository;

    @Autowired
    private UserService userService;

    private String appID = "wx2e6a8853621a12a4";

    private String appsecret = "5cac98c169850f75af1857fedf346eb8";

    private String openid;

    private String session_key;

    @PostMapping("/login")
    @LoggerManage(description = "获取微信的登录")
    public ApiResponse wxLogin(String js_code) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appID + "&secret=" + appsecret + "&js_code=" + js_code + "&grant_type=authorization_code";
        try {

            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);

            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {

                WxUser wxUser = new WxUser();
                String result = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject1 = (JSONObject) JSONObject.parse(result);
                session_key = jsonObject1.get("session_key") + "";
                openid = jsonObject1.get("openid") + "";
                if (wxUserRepository.findWxUserByOpenid(openid) == null) {
                    LocalDateTime createTime = LocalDateTime.now();
                    wxUser.setAuth(0);
                    wxUser.setRoleId(3);
                    wxUser.setCreateTime(createTime);

                    wxUser.setIsDelete(0);
                    wxUser.setOpenid(openid);
                    wxUserRepository.save(wxUser);
                }
                wxUser = wxUserRepository.findWxUserByOpenid(openid);

                if (userInfoRepository.findUserInfoByWxId(wxUser.getId()) == null){
                    UserInfo userInfo = new UserInfo();
                    userInfo.setWxId(wxUser.getId());
                    userInfo.setState(0);
                    userInfoRepository.save(userInfo);
                }
                return ApiResponse.success(wxUser);
            } else {
                logger.info("登录失败！" + response.getStatusLine().getStatusCode());
                return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
            }
        } catch (Exception e) {
            return ApiResponse.fail(new ErrorCode(1009, e.getMessage()));
        }
    }


    @PostMapping("/getUserInfo")
    @LoggerManage(description = "获取userinfo表wx用户的信息")
    public ApiResponse getUserInfo(int wxId) {
        UserInfo userInfo = userInfoRepository.findUserInfoByWxId(wxId);
        if (userInfo != null) {
            return ApiResponse.success(userInfo);
        }
        return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
    }

    @PostMapping("/user/getInfo")
    @LoggerManage(description = "小程序获取userinfo表wx用户的信息")
    public ApiResponse getInfo(int wxId) {
        UserInfo userInfo = userInfoRepository.findUserInfoByWxId(wxId);
        if (userInfo != null) {
                return ApiResponse.success(userInfo);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/getEmer")
    @LoggerManage(description = "获取紧急事件")
    public ApiResponse getEmer(int dlId) {
        Emer emer = new Emer();
        User user = userService.findUserById(dlId);
        emer.setOpenEmer(user.getOpenEmer());
        emer.setEmerTitle(user.getEmerTitle());
        emer.setEmerContent(user.getEmerContent());
        return ApiResponse.success(emer);
    }

    @Transactional
    @PostMapping("/update/phone")
    @LoggerManage(description = "更新wx用户的号码")
    public ApiResponse updatePhone(WxUser wxUser) {
        String phone = wxUser.getPhone();
        String dphone = wxUser.getDphone();
        wxUserRepository.updatePhone(phone, dphone, wxUser.getId());
        WxUser wxUser1 = wxUserRepository.findById(wxUser.getId());
        return ApiResponse.success(wxUser1);
    }

    @PostMapping("/user/update/wx")
    @LoggerManage(description = "修改我的信息")
    public ApiResponse updateWxInfo(WxUser wxUser){
        if (wxUserRepository.findById(wxUser.getId()) != null){
            wxUserRepository.updateInfo(wxUser.getNickName(),wxUser.getPhone(),wxUser.getId());
            WxUser wxUser1 = wxUserRepository.findById(wxUser.getId());
            return ApiResponse.success(wxUser1);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/get/com")
    @LoggerManage(description = "管理员获取接单员列表")
    public ApiResponse getJdList(int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        Page<WxUser> wxUsers = wxUserRepository.findJDList(pageable);
        return ApiResponse.success(wxUsers);
    }

    @PostMapping("/get/com1")
    @LoggerManage(description = "接单员列表模糊查询")
    public ApiResponse getJdList1(String nickName,int page, int size) {
        if (nickName != null){
            Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
            Page<WxUser> wxUsers = wxUserRepository.findList2(nickName,pageable);
            return ApiResponse.success(wxUsers);
        } else {
         return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }


    @PostMapping("/user/get2")
    @LoggerManage(description = "微信用户列表模糊查询")
    public ApiResponse getWxUserListById(Integer id,String nickName,String phone,String gender,int page, int size) {


        if (id != null){
            Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");

            Page<WxUser> wxUsers = wxUserRepository.findList1(id,pageable);

            return ApiResponse.success(wxUsers);
        } else if (nickName != null) {
            Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
            Page<WxUser> wxUsers = wxUserRepository.findList2(nickName,pageable);

            return ApiResponse.success(wxUsers);
        } else if (phone != null){
            Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
            Page<WxUser> wxUsers = wxUserRepository.findList3(phone,pageable);
            return ApiResponse.success(wxUsers);
        } else if (gender != null){
            Integer gender1 =0;
            if (gender.equals("男")){
                gender1 = 1;
            }
            if (gender.equals("女")){
                gender1 = 2;
            }
            Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
            Page<WxUser> wxUsers = wxUserRepository.findList4(gender1,pageable);
            return ApiResponse.success(wxUsers);
        } else {
            {
                return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
            }
        }

    }

    @PostMapping("/user/get")
    @LoggerManage(description = "获取所有的微信用户")
    public ApiResponse getWxUserList(int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");

        Page<WxUser> wxUsers = wxUserRepository.findList(pageable);

        return ApiResponse.success(wxUsers);
    }

    //todo
    @PostMapping("/user/get/todo")
    @LoggerManage(description = "获取所有的微信用户")
    public ApiResponse getAgentWxUserList(int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");

        Page<WxUser> wxUsers = wxUserRepository.findList(pageable);

        return ApiResponse.success(wxUsers);
    }

    @PostMapping("/user/get/id")
    @LoggerManage(description = "根据id获取用户信息")
    public ApiResponse getWxUser(int id) {

        WxUser wxUser = wxUserRepository.findById(id);
        if (wxUser != null) {
            return ApiResponse.success(wxUser);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/get/review")
    @LoggerManage(description = "获取审核列表")
    public ApiResponse getReview() {
        List<Map<String, Object>> list = wxUserRepository.getWxUserInfo();
        return ApiResponse.success(list);
    }

    @PostMapping("/get/review1")
    @LoggerManage(description = "获取审核列表模糊查询")
    public ApiResponse getReview1(Integer id,String nickName) {
        if (id != null){
            List<Map<String, Object>> list = wxUserRepository.getWxUserInfo1(id);
            return ApiResponse.success(list);
        } else if (nickName != null){
            List<Map<String, Object>> list = wxUserRepository.getWxUserInfo2(nickName);
            return ApiResponse.success(list);
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/user/update")
    @LoggerManage(description = "更新wx用户信息")
    public ApiResponse updateWxUserInfo(WxUser wxUser){
        if (wxUserRepository.findById(wxUser.getId()) != null){
            wxUserRepository.updateWxUserInfo(wxUser.getNickName(),wxUser.getPhone(),wxUser.getGender(),wxUser.getCity(),wxUser.getProvince(),wxUser.getAvatarUrl(),wxUser.getId());
            WxUser wxUser1 = wxUserRepository.findById(wxUser.getId());
            return ApiResponse.success(wxUser1);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/user/regis")
    @LoggerManage(description = "申请接单")
    public ApiResponse apply(UserInfo userInfo){

        UserInfo userInfo1 = userInfoRepository.findUserInfoByWxId(userInfo.getWxId());

        if (userInfo1 != null){
                userInfoRepository.applyJd(userInfo.getWxId(),userInfo.getName(),userInfo.getCardNum(),userInfo.getCert(),userInfo.getStuCard(),userInfo.getAId());
                UserInfo userInfo2 = userInfoRepository.findUserInfoByWxId(userInfo.getWxId());
                Wallets wallets = new Wallets();
                wallets.setUid(userInfo.getWxId());
                wallets.setType(1);
                walletsRepository.save(wallets);
                return ApiResponse.success(userInfo2);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/user/update/info")
    @LoggerManage(description = "修改证件信息")
    public ApiResponse updateCard(UserInfo userInfo){
     if (userInfoRepository.findUserInfoByWxId(userInfo.getWxId()) != null ){
          userInfoRepository.updateCard(userInfo.getWxId(),userInfo.getName(),userInfo.getCardNum(),userInfo.getCert(),userInfo.getStuCard(),userInfo.getAId());
          UserInfo userInfo1 = userInfoRepository.findUserInfoByWxId(userInfo.getWxId());
          return ApiResponse.success(userInfo1);
     } else {
         return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
     }
    }

    @PostMapping("/user/pass")
    @LoggerManage(description = "审核通过")
    public ApiResponse pass(int id){
        UserInfo userInfo = userInfoRepository.findById(id);
        if (userInfo != null){
            wxUserRepository.updateAuthState(userInfo.getWxId());
            userInfoRepository.updateState(userInfo.getWxId());
            Wallets wallets = new Wallets();
            wallets.setCreateTime(LocalDateTime.now());
            wallets.setType(1);
            wallets.setUid(userInfo.getWxId());
            walletsRepository.save(wallets);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/user/unpass")
    @LoggerManage(description = "审核不通过")
    public ApiResponse unPass(int id,String msg){
        UserInfo userInfo = userInfoRepository.findById(id);
        if (userInfo != null){
            wxUserRepository.updateAuthStates(userInfo.getWxId());
            userInfoRepository.updateStateMsg(userInfo.getWxId(),msg);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/user/state/disable")
    @LoggerManage(description = "禁用wx用户")
    public ApiResponse updateSate(String []id){
        for (int i = 0; i < id.length; i++) {
            wxUserRepository.updateSate(Integer.parseInt(id[i]));
        }
        return ApiResponse.success();
    }

    @PostMapping("/user/state/available")
    @LoggerManage(description = "启用wx用户")
    public ApiResponse updateSates(String []id){
        for (int i = 0; i < id.length; i++) {
            wxUserRepository.updateSates(Integer.parseInt(id[i]));
        }
        return ApiResponse.success();
    }

    @PostMapping("/jdUser/state/available")
    @LoggerManage(description = "启用wx接单用户")
    public ApiResponse updateJdUserState(String []id){
        for (int i = 0; i < id.length; i++) {
            wxUserRepository.updateJdUserState(Integer.parseInt(id[i]));
        }
        return ApiResponse.success();
    }

    @PostMapping("/jdUser/state/disable")
    @LoggerManage(description = "禁用wx接单用户")
    public ApiResponse updateJdUserStates(String []id){
        for (int i = 0; i < id.length; i++) {
            wxUserRepository.updateJdUserStates(Integer.parseInt(id[i]));
        }
        return ApiResponse.success();
    }


}
