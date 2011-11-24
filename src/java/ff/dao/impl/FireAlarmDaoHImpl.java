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

        return fireAlarm;
    }

    @Override
    public String delFireAlarmAll(String id) {
        Session s = this.getHibernateTemplate().getSessionFactory().openSession();

        String sql = "delete from fire_alarms where id in " + "(" + id + ")";

        Query q = s.createSQLQuery(sql);
        q.executeUpdate();

        return "success";


    }

    @Override
    public List<FireAlarm> getFireAlarmsByPtzId(Long ptzId) {
        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm f where f.ptz.id = ?", ptzId);
        return fireAlarms;
    }

    @Override
    public List getFireAlarmsByBeginTime(Timestamp beginTime) {
        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm f where f.actionDate > ?", beginTime);
        return fireAlarms;
    }

    @Override
    public List getFireAlarmsByEndTime(Timestamp endTime) {
        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm f where f.actionDate <= ?", endTime);
        return fireAlarms;
    }

    @Override
    public List getFireAlarmsByEndTimeBeginTime(Timestamp endTime, Timestamp beginTime) {
        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm f where f.actionDate <= ? and f.actionDate >= ?", endTime, beginTime);
        return fireAlarms;
    }

    @Override
    public List getFireAlarmsByBeginTimePtzId(Long ptzId, Timestamp beginTime) {
        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm f where f.actionDate >= ? and f.ptz.id = ?", beginTime, ptzId);
        return fireAlarms;
    }

    @Override
    public List getFireAlarmsByEndTimePtzId(Long ptzId, Timestamp endTime) {
        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm f where f.actionDate <= ? and f.ptz.id = ?", endTime, ptzId);
        return fireAlarms;
    }

    @Override
    public List getFireAlarmsByBeginTimePtzId(Long ptzId, Timestamp beginTime, Timestamp endTime) {
        List<FireAlarm> fireAlarms = this.getHibernateTemplate().find("from FireAlarm f where f.actionDate <= ? and f.ptz.id = ? and f.actionDate >= ?", endTime, ptzId, beginTime);
        return fireAlarms;
    }
}
