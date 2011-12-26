/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.RoleDao;
import ff.dao.UserDao;
import ff.model.Role;
import ff.model.User;
import ff.service.UserService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author jerry
 */
public class UserServiceImpl implements UserService {

    static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    //注入区：
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    private RoleDao roleDao;

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /* 新的方法 */
    @Override
    public String create(User user) {
        String info = null;
        if (userDao.getUserByName(user.getName()) == null) {
            userDao.saveOrUpdate(user);
            info = "success";
        } else {
            info = "该用户名已使用，请更换！";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String deleteUser(Long id) {
        User user = userDao.getUserById(id);
        String info = null;
        if (user == null) {
            info = "没有该用户，不能删除！";
        } else {
            userDao.delete(user);
            info = "success";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String getUserJSONById(Long id) {
        User user = userDao.getUserById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"videos", "users","rolesPrivilegeDetails", "rolePtz","fireAlarm"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONObject userJS = JSONObject.fromObject(user, jsonConfig);
        String jsonStr = userJS.toString();
        return jsonStr;
    }

    @Override
    public String update(User user) {
        String info = null;
        if (user == null) {
            info = "没有该用户，不能编辑！";
        } else {
            User userDB = userDao.getUserByName(user.getName());
            if (userDB != null && userDB.getId() != user.getId()) {
                info = "该用户名已使用，请更换！";
            } else {
                userDao.saveOrUpdate(user);
                info = "success";
            }
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String getUserList() {
        List users = userDao.getAllUsers();
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        jsonConfig.setExcludes(new String[]{"fireAlarm","rolesPrivilegeDetails","rolePtz","ptz"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray userJS = JSONArray.fromObject(users, jsonConfig);
        String jsonStr = "{totalProperty:" + users.size() + ",root:" + userJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public String validateUser(String loginId, String password) {
        String info = null;
        User user = userDao.findUser(loginId, password);
        if (user == null) {
            info = "'用户名或密码错误!'";
        } else {
          
            info = "'success',id:" + String.valueOf(user.getId());                                //如果验证成功，就将id返回
        }
        String jsonStr = "{success:true,info:" + info + "}";
        return jsonStr;
    }

    /**
     * @author Joey
     * 通过用户名得到角色id
     */
    @Override
    public String getRoleIdByUserName(String name) {
        return String.valueOf(userDao.getUserByName(name).getRole().getId());   //通过用户名得到user对象，通过user对象得到role对象，最后得到roleId
    }

    /**
     * @author Joey
     * 通过用户名得到角色id
     */
    @Override
    public String getRoleIdByUserId(Long id) {
        return String.valueOf(userDao.getUserById(id).getRole().getId());   //通过用户名得到user对象，通过user对象得到role对象，最后得到roleId
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

   
}
