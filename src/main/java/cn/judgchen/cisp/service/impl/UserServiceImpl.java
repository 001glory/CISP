package cn.judgchen.cisp.service.impl;

import cn.judgchen.cisp.dao.UserRepository;
import cn.judgchen.cisp.entity.User;
import cn.judgchen.cisp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
        logger.info(user.getUsername()+"用户写入成功！");
    }

    @Override
    @Transactional
    public void deleteUserByUserame(String username) {
        userRepository.deleteByUsername(username);
        logger.info(username+"删除成功！");
    }

    @Override
    public User selectUserByUsername(String username,String pwd) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional
    public void updatePasswordByusername(String username,String newPassword) {
        userRepository.updatePasswordBy(username,newPassword);
        logger.info(username+"修改成功！");
    }

    public User findUserById(int uid){
        return userRepository.findUserByPkId(uid);
    }

    @Override
    public User findUserByAId(int aId) {
        return userRepository.findUserByAId(aId);
    }

    @Override
    public void updateEmer(int pkId, int openEmer, String emerTitle, String emerContent) {
        userRepository.updateEmer(pkId,openEmer,emerTitle,emerContent);
    }
}
