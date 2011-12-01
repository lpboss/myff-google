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
import java.util.Calendar;
import java.sql.Timestamp;

/**
 *
 * @author Haoqingmeng
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

    //得到rolePtz的某一条数据
    @Override
    public RolePtz getRolePtzById(Long id, Long roleid) {
//        List<RolePtz> rolePtz =  this.getHibernateTemplate().find("from RolePtz where role_id =" + roleid + " and id =" +  id );
   //     RolePtz rolePtz = (RolePtz) this.getHibernateTemplate().find("from RolePtz where role_id =" + roleid + " and id =" + id);
          RolePtz rolePtz = (RolePtz) this.getHibernateTemplate().get(RolePtz.class, id );
        
        logger.info("133f2v2");
        logger.info(rolePtz);
        logger.info(rolePtz.getPtz().getName());
        logger.info("133f2v2");
        return rolePtz;
    }

    //处理是否默认云台
    @Override
    public void setDefault(Long id) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        String sql = "update role_ptzs set is_default = 0 where role_id =" + id;
        Query q = s.createSQLQuery(sql);
        q.executeUpdate();
    }

    //通过id得到rolePtz列表
    @Override
    public List<RolePtz> getById(Integer id) {
        List<RolePtz> rolePtzs = this.getHibernateTemplate().findByNamedParam("from RolePtz where role_id =:id", new String[]{"id"}, new Object[]{id});
        return rolePtzs;
    }

    @Override
    public List<PTZ> getPtzsByIds(String ids) {
        List<PTZ> ptzs = this.getHibernateTemplate().find("from PTZ where id in (" + ids + ")");
        return ptzs;

    }

    //保存并更新
    @Override
    public RolePtz saveOrUpdate(RolePtz rolePtz) {
        this.getHibernateTemplate().saveOrUpdate(rolePtz);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return rolePtz;
    }

    //删除
    @Override
    public void deleteRolePtz(String id, String roleid) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        String sql = "delete from role_ptzs where role_id =" + roleid + " and id in" + "(" + id + ")";
        System.out.println(sql);
        Query q = s.createSQLQuery(sql);
        q.executeUpdate();
    }

    //得到所有的rolePtz列表
    @Override
    public List<RolePtz> getAllRolePtzs() {
        List<RolePtz> rolePtzs = this.getHibernateTemplate().find("from RolePtz" + " order by role_id");
        return rolePtzs;
    }

    //通过id得到rolePtz数据
    @Override
    public List<RolePtz> getRolePtzByIds(String ids) {
        List<RolePtz> rolePtzs = this.getHibernateTemplate().find("from RolePtz where ptz_id in (" + ids + ")" + " order by role_id");
        return rolePtzs;
    }

    //判断有没有重名的  where role_id ="+ id
    @Override
    public RolePtz getRolePtzByName(Long ptzId, Long roleId) {
        List<RolePtz> rolePtzs = this.getHibernateTemplate().find("from RolePtz where role_id=" + roleId + "and" + " ptz_id=" + ptzId);
        if (rolePtzs.size() > 0) {
            return rolePtzs.get(0);
        } else {
            return null;
        }
    }

//    //通过id得到rolePtz列表
//    @Override
//    public RolePtz getRolePtzsById(Integer id) {
//       //  List<RolePtz> rolePtzs = this.getHibernateTemplate().findByNamedParam("from RolePtz where role_id =:id", new String[]{"id"}, new Object[]{id});
//        RolePtz rolePtzs = (RolePtz)  this.getHibernateTemplate().get(Role.class, id);
//         return rolePtzs;
//    }

    //通过id得到rolePtz列表
    @Override
    public RolePtz getRolePtzsById(Long id) {
        System.out.println("432c12431c");
        System.out.println(id);
        System.out.println("1ccvtb5");
        RolePtz rolePtzs = (RolePtz)  this.getHibernateTemplate().get(RolePtz.class, id);
        System.out.println("1fc1d1");
        System.out.println(rolePtzs);
        System.out.println("12x45g");
        return rolePtzs;
    }
    
}
