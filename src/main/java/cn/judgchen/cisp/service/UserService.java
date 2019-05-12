package cn.judgchen.cisp.service;


import cn.judgchen.cisp.entity.User;

public interface UserService {

    /**
     * 新增一个用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据用户名删除用户
     * @param username
     */
    void deleteUserByUserame(String username);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User selectUserByUsername(String username, String pwd);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User selectUserByUsername(String username);

    /**
     * 根据用户名修改密码
     * @param username
     */
    void updatePasswordByusername(String username, String newPassword);


    User findUserById(int uid);

    User findUserByAId(int aId);

    void updateEmer(int pkId, int openEmer, String emerTitle, String emerContent);


}
