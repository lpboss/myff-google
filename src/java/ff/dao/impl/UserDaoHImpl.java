/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.UserDao;
import ff.model.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author jerry
 */
public class UserDaoHImpl extends HibernateDaoSupport implements UserDao {

    @Override
    public User getUser(String name, String password) {
        List<User> users = this.getHibernateTemplate().findByNamedParam(
                "from User where name=:name and password=:password",
                new String[]{"name", "password"}, new String[]{name, password});
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUserByName(String name) {
        List<User> users = this.getHibernateTemplate().findByNamedParam(
                "from User where name=:name", new String[]{"name"},
                new String[]{name});
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = this.getHibernateTemplate().find("from User");
        return users;
    }

    @Override
    public User saveOrUpdate(User user) {
        //以下二句可写在其它部分，这里只是提示一下如何生成TimeStamp
        if (user.getId() == null) {
            user.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        user.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        this.getHibernateTemplate().save(user);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = (User) this.getHibernateTemplate().get(User.class, id);
        return user;
    }

    @Override
    public void delete(User user) {
        this.getHibernateTemplate().delete(user);
    }

    //用于登录页登录时的验证
    @Override
    public User findUser(String loginId, String password) {
        ArrayList temp = (ArrayList) this.getHibernateTemplate().findByNamedParam("from User where login_id = :login_id and password=:password", new String[]{"login_id", "password"}, new String[]{loginId, password});
        if (temp.isEmpty() == true) {
            return null;
        } else {
            User user = (User) temp.get(0);
            return user;
        }
    }
}
