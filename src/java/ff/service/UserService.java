/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.Role;
import ff.model.User;

/**
 *
 * @author jerry
 */
public interface UserService {

//    User saveOrUpdate(User user);
//
//    User getUser(String name, String password);
//
//    User getUserByName(String name);
//
//
//    List<User> getAllUsers();
//
//    void delete(User user);
//    User findUser(String name, String password, String admin);

    /* 新的方法 */
    String create(User user);

    String deleteUser(Long id);

    String getUserById(Long id);

    String update(User user);

    String getUserList();

    String validateUser(String loginId, String password);

    /**
     * @author Joey
     * 根据用户名得到角色id
     */
    String getRoleIdByUserName(String name);

    /**
     * @author Joey
     * 通过用户名得到角色id
     */
    String getRoleIdByUserId(Long id);
    
    
}
