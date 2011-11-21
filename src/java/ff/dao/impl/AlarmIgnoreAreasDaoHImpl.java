/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.AlarmIgnoreAreasDao;
import ff.model.AlarmIgnoreAreas;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Administrator
 */
public class AlarmIgnoreAreasDaoHImpl extends HibernateDaoSupport implements AlarmIgnoreAreasDao {

    @Override
    public List<AlarmIgnoreAreas> getAlarmIgnoreAreases() {
        List<AlarmIgnoreAreas> alarmIgnoreAreases = this.getHibernateTemplate().find("from AlarmIgnoreAreas");
        return alarmIgnoreAreases;
    }

    @Override
    public AlarmIgnoreAreas saveOrUpdate(AlarmIgnoreAreas alarmIgnoreAreas) {
        if (alarmIgnoreAreas.getId() == null) {
            alarmIgnoreAreas.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        alarmIgnoreAreas.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));

        //正式开始存储数据
        this.getHibernateTemplate().saveOrUpdate(alarmIgnoreAreas);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return alarmIgnoreAreas;
    }

    @Override
    public AlarmIgnoreAreas getAlarmIgnoreAreasById(Long id) {
        AlarmIgnoreAreas alarmIgnoreAreas = (AlarmIgnoreAreas) this.getHibernateTemplate().get(AlarmIgnoreAreas.class, id);
        return alarmIgnoreAreas;
    }

    @Override
    public AlarmIgnoreAreas getAlarmIgnoreAreasByName(String name) {
         List<AlarmIgnoreAreas> alarmIgnoreAreases = this.getHibernateTemplate().findByNamedParam("from PTZ where name=:name", new String[]{"name"}, new String[]{name});
        if (alarmIgnoreAreases.size() > 0) {
            return alarmIgnoreAreases.get(0);
        } else {
            return null;
        }
    }
    
}
