/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.IgnoreAreasDao;
import ff.model.IgnoreAreas;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public class IgnoreAreasDaoHImpl extends HibernateDaoSupport implements IgnoreAreasDao {

    @Override
    public List<IgnoreAreas> getIgnoreAreases() {
        List<IgnoreAreas> ignoreAreases = this.getHibernateTemplate().find("from IgnoreAreas");
        return ignoreAreases;
    }

    @Override
    public IgnoreAreas saveOrUpdate(IgnoreAreas ignoreAreas) {
        if (ignoreAreas.getId() == null) {
            ignoreAreas.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        ignoreAreas.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));

        //正式开始存储数据
        this.getHibernateTemplate().saveOrUpdate(ignoreAreas);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return ignoreAreas;
    }

    @Override
    public List<IgnoreAreas> getById(Integer id) {
        //  IgnoreAreas ignoreAreases = (IgnoreAreas) this.getHibernateTemplate().get(IgnoreAreas.class, id);       
        //  return ignoreAreases;
        System.out.print("44444444");
        System.out.print(id);
        List<IgnoreAreas> ignoreAreases = this.getHibernateTemplate().findByNamedParam("from IgnoreAreas where ptz_id =:id", new String[]{"id"}, new Object[]{id});
        //     List ignoreAreases = this.getHibernateTemplate().find("from IgnoreArea as i where i.ptzId = ?",id);
    
        System.out.println("535235");
        System.out.println(ignoreAreases.get(0).getId());
        System.out.println(ignoreAreases.get(1).getId());
        System.out.println(ignoreAreases.get(2).getId());
        System.out.println("67458568");
        return ignoreAreases;
    }

    @Override
    public IgnoreAreas getIgnoreAreasByName(String name) {
        List<IgnoreAreas> ignoreAreases = this.getHibernateTemplate().findByNamedParam("from PTZ where name=:name", new String[]{"name"}, new String[]{name});
        if (ignoreAreases.size() > 0) {
            return ignoreAreases.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteIgnoreAreas(String id) {

        Session s = this.getHibernateTemplate().getSessionFactory().openSession();
        String sql = "delete from ignore_areas where id in " + "(" + id + ")";
        Query q = s.createSQLQuery(sql);
        q.executeUpdate();
    }

    @Override
    public IgnoreAreas getIgnoreAreasById(Integer id) {
        System.out.println("222ccc");
        IgnoreAreas ignoreAreas = (IgnoreAreas) this.getHibernateTemplate().get(IgnoreAreas.class, id);
        return ignoreAreas;
    }
}
