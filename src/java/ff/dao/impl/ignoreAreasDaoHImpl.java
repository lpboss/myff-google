/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.ignoreAreasDao;
import ff.model.IgnoreAreas;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Administrator
 */
public class ignoreAreasDaoHImpl extends HibernateDaoSupport implements ignoreAreasDao {

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
    public IgnoreAreas getIgnoreAreasById(Long id) {
        IgnoreAreas ignoreAreases = (IgnoreAreas) this.getHibernateTemplate().get(IgnoreAreas.class, id);
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
    public String deleteIgnoreAreas(Long id) {
        try {
            Object role = this.getHibernateTemplate().load(IgnoreAreas.class, new Long(id));    //先加载特定实例 
            getHibernateTemplate().delete(role);                                 //删除特定实例
        } catch (Exception e) {
            return e.toString();
        }
        return "success";
    }
}
