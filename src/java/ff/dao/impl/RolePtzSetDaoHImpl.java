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
        logger.info("12s2");
        List<PTZ> ptzs = this.getHibernateTemplate().find("from PTZ");
         logger.info("54hbiik68");
         logger.info("12s2");
         logger.info("78kjgb3");
        return ptzs;
    }

    //得到ptz的某一条数据
    @Override
    public PTZ getPTZById(Long id) {
        PTZ ptz = (PTZ) this.getHibernateTemplate().get(PTZ.class, id);
        return ptz;
    }

    //处理是否默认云台
    @Override
    public void setDefault(Long id) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();

        String sql = "update role_ptzs set is_default = 0 where role_id ="+ id;

        Query q = s.createSQLQuery(sql);
        q.executeUpdate();
    }

    @Override
    public List<RolePtz> getById(Integer id) {

        System.out.print("44444444");
        System.out.print(id);
        List<RolePtz> rolePtzs = this.getHibernateTemplate().findByNamedParam("from RolePtz where role_id =:id", new String[]{"id"}, new Object[]{id});
        System.out.print("54343");
        System.out.print(rolePtzs.size());
        System.out.print("6758898");
        return rolePtzs;
    }

    @Override
    public List<PTZ> getPtzsByIds(String ids) {
        System.out.println("67567");
        System.out.println(ids);
        List<PTZ> ptzs = this.getHibernateTemplate().find("from PTZ where id in (" + ids + ")");
        //     List<PTZ> ptzs = this.getHibernateTemplate().find("from PTZ where id in (3,9)");
        System.out.println("87978");
        System.out.println("from ptzs where id in (" + ids + ")");
        System.out.println(ptzs);
        System.out.println("67723");
        return ptzs;

    }

    //保存并更新
    @Override
    public RolePtz saveOrUpdate(RolePtz rolePtz) {
        logger.info("45tvgthy");
        logger.info(rolePtz);
        logger.info(rolePtz);
        logger.info(rolePtz.getRole().getName());
        logger.info("trg465");

        this.getHibernateTemplate().saveOrUpdate(rolePtz);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        logger.info("12wwsd");
        logger.info(rolePtz);
        logger.info("24tg5");

        return rolePtz;
    }

    //删除
    @Override
    public void deleteRolePtz(String id, String roleid) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        logger.info("54b756");
         logger.info(id);
          logger.info(roleid);
           logger.info("324fvy6uk7");
        String sql = "delete from role_ptzs where role_id =" + roleid + " and id in" + "(" + id + ")";
        System.out.println(sql);
        Query q = s.createSQLQuery(sql);
        q.executeUpdate();
    }

    //得到所有的rolePtz列表
    @Override
    public List<RolePtz> getAllRolePtzs() {
         logger.info("12wwsd");

         List<RolePtz> rolePtzs = this.getHibernateTemplate().find("from RolePtz"+" order by role_id");
          logger.info("454g36576");
        logger.info(rolePtzs);
        logger.info("12fv57jb");
        return rolePtzs;
    }

    //通过id得到rolePtz数据
    @Override
    public List<RolePtz> getRolePtzByIds(String ids) {
        System.out.println("67567");
        System.out.println(ids);
        List<RolePtz> rolePtzs = this.getHibernateTemplate().find("from RolePtz where ptz_id in (" + ids + ")"+" order by role_id");       
        System.out.println("87978");
        System.out.println("from ptzs where id in (" + ids + ")");
        System.out.println(rolePtzs);
        System.out.println("67723");
        return rolePtzs;
    }

    //判断有没有重名的  where role_id ="+ id
    @Override
    public RolePtz getRolePtzByName(Long ptzId,Long roleId) {
        List<RolePtz> rolePtzs = this.getHibernateTemplate().find("from RolePtz where role_id="+ roleId + "and" + " ptz_id="+ ptzId);  
        if (rolePtzs.size() > 0) {
            return rolePtzs.get(0);
        } else {
            return null;
        }
    }

    
    
    
}
