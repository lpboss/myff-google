/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.PTZDao;
import ff.model.PTZ;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Haoqingmeng
 */
public class PTZDaoHImpl extends HibernateDaoSupport implements PTZDao {

    @Override
    public PTZ saveOrUpdate(PTZ ptz) {
        if (ptz.getId() == null) {
            ptz.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        ptz.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        System.out.println("lllllllllllllllllllllllllllll");
        //正式开始存储数this.getHibernateTemplate().saveOrUpdate(ptz);
        this.getHibernateTemplate().saveOrUpdate(ptz);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        System.out.print("9090");
        System.out.print(ptz);
        return ptz;

    }

    //得到所有的ptz列表信息
    @Override
    public List<PTZ> getAllPTZs() {
        List<PTZ> ptzs = this.getHibernateTemplate().find("from PTZ");
        return ptzs;
    }

    @Override
    public PTZ getPTZById(Long id) {
        PTZ ptz = (PTZ) this.getHibernateTemplate().get(PTZ.class, id);
        return ptz;
    }

    @Override
    public PTZ getPTZByName(String name) {
        List<PTZ> ptzs = this.getHibernateTemplate().findByNamedParam("from PTZ where name=:name", new String[]{"name"}, new String[]{name});
       
        if (ptzs.size() > 0) {
            return ptzs.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<PTZ> getIsAlarmPTZs() {
        List<PTZ> ptzs = this.getHibernateTemplate().findByNamedParam("from PTZ where is_alarm = :is_alarm", new String[]{"is_alarm"}, new Integer[]{1});
        return ptzs;
    }

    @Override
    public void deletePTZ(String id) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();

        String sql = "delete from ptzs where id in " + "(" + id + ")";

        Query q = s.createSQLQuery(sql);
        q.executeUpdate();

    }
}
