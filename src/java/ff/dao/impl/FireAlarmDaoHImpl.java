/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import com.mysql.jdbc.Connection;
import ff.dao.FireAlarmDao;
import ff.model.FireAlarm;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Administrator
 */
public class FireAlarmDaoHImpl extends HibernateDaoSupport implements FireAlarmDao {

    @Override
    public List<FireAlarm> getAllFireAlarms() {

        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm");
        return fireAlarms;
    }

    @Override
    public FireAlarm saveOrUpdate(FireAlarm fireAlarm) {
        if (fireAlarm.getId() == null) {
            fireAlarm.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        fireAlarm.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        

        this.getHibernateTemplate().saveOrUpdate(fireAlarm);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return fireAlarm;
    }

    @Override
    public void delete(FireAlarm fireAlarm) {

        this.getHibernateTemplate().delete(fireAlarm);

    }

    @Override
    public FireAlarm getFireAlarmById(Long id) {
        FireAlarm fireAlarm = (FireAlarm) this.getHibernateTemplate().get(FireAlarm.class, id);

        System.out.println("cccccccccccccccccccccccccccccccccccccccccc");
        return fireAlarm;
    }

    @Override
    public void delFireAlarmAll(String id) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();

        String sql = "delete from fire_alarms where id in " + "(" + id + ")";

        Query q = s.createSQLQuery(sql);
        q.executeUpdate();




    }
}
