/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.RolePtzSetDao;
import ff.model.PTZ;
import ff.model.Role;
import ff.model.RolePtz;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public class RolePtzSetDaoHImpl extends HibernateDaoSupport implements RolePtzSetDao {

    //得到所有的角色
    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = this.getHibernateTemplate().find("from Role");
        return roles;
    }

    //得到所有ptz信息
    @Override
    public List<PTZ> getAllPTZs() {
        List<PTZ> ptzs = this.getHibernateTemplate().find("from PTZ");

        return ptzs;
    }

    //得到ptz的某一条数据
    @Override
    public PTZ getPTZById(Long id) {
        PTZ ptz = (PTZ) this.getHibernateTemplate().get(PTZ.class, id);
        return ptz;
    }

    @Override
    public void setDefault(Long id) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();

        String sql = "update ptzs set is_default = 0";

        Query q = s.createSQLQuery(sql);
        q.executeUpdate();
    }

    @Override
    public List<RolePtz> getById(Integer id) {

        System.out.print("44444444");
        System.out.print(id);
        List<RolePtz> rolePtzs = this.getHibernateTemplate().findByNamedParam("from RolePtz where role_id =:id", new String[]{"id"}, new Object[]{id});
        return rolePtzs;
    }

    @Override
    public List<PTZ> getPtzsByIds(String ids) {
        List<PTZ> ptzs = this.getHibernateTemplate().find("from ptzs where id in ("+ids+")");
        System.out.println("from ptzs where id in ("+ids+")");
        System.out.println("ppppppp");
        return ptzs;

    }
}
