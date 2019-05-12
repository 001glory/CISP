package cn.judgchen.cisp.controller;


import cn.judgchen.cisp.common.aop.LoggerManage;
import cn.judgchen.cisp.common.code.ConstanCode;
import cn.judgchen.cisp.common.model.response.ApiResponse;
import cn.judgchen.cisp.dao.UserRepository;
import cn.judgchen.cisp.entity.*;
import cn.judgchen.cisp.service.UserService;
import cn.judgchen.cisp.utils.DifferenceTime;
import cn.judgchen.cisp.utils.MyMD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@RestController()
@RequestMapping("/user")
public class UserController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    @LoggerManage(description = "注册用户")
    public ApiResponse register(String username, String pwd, int aid, int dtype, String phone, Date deadLine) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(userService.selectUserByUsername(username)==null){
            User user = new User();
            user.setUsername(username);
            LocalDateTime register_time = LocalDateTime.now();
            user.setPwd(MyMD5Util.getEncryptedPwd(pwd));
            user.setAId(aid);
            user.setDtype(dtype);
            user.setPhone(phone);
            Instant instant = deadLine.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();

            user.setIsDelete(0);
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            user.setDeadline(localDateTime);
            user.setCreateTime(register_time);
            user.setModifyTime(register_time);
            userService.addUser(user);
            return ApiResponse.success();
        }else {
            return ApiResponse.fail(ConstanCode.USER_HAS_REGISTERED);
        }

    }
    @DeleteMapping("/delete")
    @Transactional
    @LoggerManage(description = "删除用户")
    public ApiResponse deleteUser(String username){
        if (userService.selectUserByUsername(username)!=null){
            userService.deleteUserByUserame(username);
            return ApiResponse.success();
        }else {
            return ApiResponse.fail(ConstanCode.IDENTITY_CHECK_FAILED);
        }
    }

    @PostMapping("/login")
    @LoggerManage(description = "登录")
    public ApiResponse login(String username, String pwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("密码："+pwd);
        logger.info("用户名："+username);
        User user = userService.selectUserByUsername(username,pwd);

        if(user!=null){
            logger.info("数据库的密码："+user.getPwd());
            if(MyMD5Util.validPassword(pwd,user.getPwd())){
                logger.info("登录成功");
                return ApiResponse.success(user);
            }
            return ApiResponse.fail(ConstanCode.WRONG_PASSWORD);
        }else {
            return ApiResponse.fail(ConstanCode.IDENTITY_CHECK_FAILED);
        }
    }

    @PostMapping("/updatePwd")
    @Transactional
    @LoggerManage(description = "修改密码")
    public ApiResponse updatePassword(String username,String oldPwd,String newPwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        User user = userService.selectUserByUsername(username);
        LocalDateTime modify_time = LocalDateTime.now();
        if(user!=null){
            if(MyMD5Util.validPassword(oldPwd,user.getPwd())){
                if(DifferenceTime.difference(modify_time)- DifferenceTime.difference(user.getModifyTime())>5){
                    user.setModifyTime(modify_time);
                    userService.updatePasswordByusername(username,MyMD5Util.getEncryptedPwd(newPwd));
                    return ApiResponse.success();
                } else {
                    return ApiResponse.fail(ConstanCode.FREQUENT_OPERATION);
                }
            } else {
                return ApiResponse.fail(ConstanCode.WRONG_PASSWORD);
            }

        } else {
            return ApiResponse.fail(ConstanCode.IDENTITY_CHECK_FAILED);
        }
    }

    @GetMapping("/getInfo")
    @LoggerManage(description = "获取用户信息")
    public ApiResponse getUserInfo(int uid){
        User user = userService.findUserById(uid);
        AdminUserInfo adminUserInfo = new AdminUserInfo();
        adminUserInfo.setUsername(user.getUsername());
        adminUserInfo.setUser_sate(user.getUserState());
        adminUserInfo.setCreate_time(user.getCreateTime());
        adminUserInfo.setPhone(user.getPhone());
        return ApiResponse.success(adminUserInfo);
    }


    @PostMapping("/getByAid")
    @LoggerManage(description = "根据aid获取用户信息")
    public ApiResponse getInfoByAid(int aid){
        User user = userService.findUserByAId(aid);
        return ApiResponse.success(user);
    }

    @PostMapping("/getEmer")
    @LoggerManage(description = "admin获取紧急事件")
    public ApiResponse getEmer(int uid){
        //获取用户信息
        User user = userService.findUserById(uid);
        if(user != null){
            Emer emer = new Emer();
            emer.setOpenEmer(user.getOpenEmer());
            emer.setEmerTitle(user.getEmerTitle());
            emer.setEmerContent(user.getEmerContent());
            return ApiResponse.success(emer);
        } else {
            return ApiResponse.fail(ConstanCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/updateEmer")
    @LoggerManage(description = "更新紧急事件信息")
    public ApiResponse updateEmer(int uid,int openEmer,String emerTitle,String emerContent){
        if (userService.findUserById(uid) != null){
            userService.updateEmer(uid,openEmer,emerTitle,emerContent);
            return ApiResponse.success();
        } else {
            return ApiResponse.fail(ConstanCode.RECORD_DOES_NOT_EXIST);
        }
    }


    @PostMapping("/get")
    @LoggerManage(description = "获取代理用户信息")
    public ApiResponse getDLUser(int page,int size){
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"create_time");
        Page<User>  users = userRepository.findDLUser(pageable);
        return ApiResponse.success(users);
    }

}
