/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.FireAlarmDao;
import ff.model.FireAlarm;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
}
