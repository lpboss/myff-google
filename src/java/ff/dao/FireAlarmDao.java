/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.FireAlarm;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface FireAlarmDao {

    List<FireAlarm> getAllFireAlarms();

    FireAlarm saveOrUpdate(FireAlarm fireAlarm);

    void delete(FireAlarm fireAlarm);

    FireAlarm getFireAlarmById(Long id);

    void delFireAlarmAll(String id);

    List<FireAlarm> getFireAlarmsByPtzId(Long ptzId);

    public List getFireAlarmsByBeginTime(Timestamp beginTime);

    public List getFireAlarmsByEndTime(Timestamp endTime);

    public List getFireAlarmsByEndTimeBeginTime(Timestamp endTime, Timestamp beginTime);

    public List getFireAlarmsByBeginTimePtzId(Long ptzId, Timestamp endTime);

    public List getFireAlarmsByEndTimePtzId(Long ptzId, Timestamp endTime);

    public List getFireAlarmsByBeginTimePtzId(Long ptzId, Timestamp beginTime, Timestamp endTime);
}
