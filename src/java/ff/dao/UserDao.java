/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.User;
import java.util.List;

/**
 *
 * @author jerry
 */
public interface UserDao {

    User saveOrUpdate(User user);

    User getUser(String loginId, String password);

    User getUserByName(String name);

    User getUserById(Long id);

    List<User> getAllUsers();

    void delete(User user);

    User findUser(String loginId, String password);
}
