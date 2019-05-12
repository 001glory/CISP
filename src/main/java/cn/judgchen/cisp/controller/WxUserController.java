package cn.judgchen.cisp.controller;

import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.code.ErrorCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.UserInfoRepository;
import cn.judgchen.cisp.dao.WxUserRepository;
import cn.judgchen.cisp.entity.Emer;
import cn.judgchen.cisp.entity.User;
import cn.judgchen.cisp.entity.UserInfo;
import cn.judgchen.cisp.entity.WxUser;
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
    private UserService userService;

    private String appID = "wx2e6a8853621a12a4";

    private String appsecret = "5cac98c169850f75af1857fedf346eb8";

    private String openid;

    private String session_key;

    @PostMapping("/login")
    @LoggerManage(description = "获取微信的登录")
    public ApiResponse wxLogin(String js_code){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appID+"&secret="+appsecret+"&js_code="+js_code+"&grant_type=authorization_code";
        try {

            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);

            response = httpClient.execute(httpGet);

            if(response.getStatusLine().getStatusCode() == 200){

                WxUser wxUser = new WxUser() ;
                String result = EntityUtils.toString(response.getEntity());
//                String res = AesCbcUtil
                JSONObject jsonObject1 = (JSONObject) JSONObject.parse(result);
                session_key = jsonObject1.get("session_key")+"";
                openid  = jsonObject1.get("openid")+"";
                String formData = null;
//                String result1 = AesCbcUtil.decrypt(encryptedData,session_key,iv,formData);
//                JSONObject jsonObject1 = (JSONObject) JSONObject.parse(result1);
                String nickName =jsonObject1.get("nick_name")+"";
                String gender = jsonObject1.get("gender")+"";
                String province = jsonObject1.get("province")+"";
                String avatarUrl = jsonObject1.get("avatar_url")+"";
                logger.info(session_key+"######"+openid+"#####"+nickName);
                if(wxUserRepository.findWxUserByOpenid(openid)==null){
                    LocalDateTime createTime = LocalDateTime.now();
                    wxUser.setCreateTime(createTime);

                    wxUser.setOpenid(openid);
                    wxUserRepository.save(wxUser);
                }
                wxUser = wxUserRepository.findWxUserByOpenid(openid);
                return ApiResponse.success(wxUser);
            }else {
                logger.info("登录失败！"+response.getStatusLine().getStatusCode());
                return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
            }
        }catch (Exception e){
            return ApiResponse.fail(new ErrorCode(1009,e.getMessage()));
        }
    }


    @PostMapping("/getUserInfo")
    @LoggerManage(description = "获取userinfo表wx用户的信息")
    public ApiResponse getUserInfo(int wxId){
       UserInfo userInfo = userInfoRepository.findUserInfoByWxId(wxId);
       if (userInfo!=null){
           return ApiResponse.success(userInfo);
       }
        return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
    }

    @PostMapping("/getEmer")
    @LoggerManage(description = "获取紧急事件")
    public ApiResponse getEmer(int dlId){
        User user = userService.findUserById(dlId);
        Emer emer =new Emer();

        emer.setOpenEmer(user.getOpenEmer());
        emer.setEmerTitle(user.getEmerTitle());
        emer.setEmerContent(user.getEmerContent());
        return ApiResponse.success(emer);
    }

    @Transactional
    @PostMapping("/update/phone")
    @LoggerManage(description = "更新wx用户的号码")
    public ApiResponse updatePhone(WxUser wxUser){
        String phone = wxUser.getPhone();
        String dphone = wxUser.getDphone();
        wxUserRepository.updatePhone(phone,dphone,wxUser.getId());
        WxUser wxUser1 = wxUserRepository.findById(wxUser.getId());
        return ApiResponse.success(wxUser1);
    }

    @PostMapping("/get/com")
    @LoggerManage(description = "获取接单员列表")
    public ApiResponse getJdList(int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        Page<WxUser>  wxUsers = wxUserRepository.findJDList(pageable);
        return ApiResponse.success(wxUsers);
    }


    @PostMapping("/user/get")
    @LoggerManage(description = "获取所有的微信用户")
    public  ApiResponse getWxUserList(int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");

        Page<WxUser> wxUsers = wxUserRepository.findList(pageable);

        return ApiResponse.success(wxUsers);
    }

    @PostMapping("/user/get/id")
    @LoggerManage(description = "根据id获取用户信息")
    public ApiResponse getWxUser(int id){

        WxUser wxUser = wxUserRepository.findById(id);
        if (wxUser != null){
            return ApiResponse.success(wxUser);
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }

    @PostMapping("/get/review")
    @LoggerManage(description = "获取审核列表")
    public ApiResponse getReview(){
        List<Map<String,Object>> list = wxUserRepository.getWxUserInfo();
        return ApiResponse.success(list);
    }
}
