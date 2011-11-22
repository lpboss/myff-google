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

    List<FireAlarm> getFireAlarmsByPtzId(Integer ptzId);

    public List getFireAlarmsByBeginTime(Timestamp beginTime);

    public List getFireAlarmsByEndTime(Timestamp endTime);

    public List getFireAlarmsByEndTimeBeginTime(Timestamp endTime, Timestamp beginTime);

    public List getFireAlarmsByBeginTimePtzId(Integer ptzId, Timestamp endTime);

    public List getFireAlarmsByEndTimePtzId(Integer ptzId, Timestamp endTime);

    public List getFireAlarmsByBeginTimePtzId(Integer ptzId, Timestamp beginTime, Timestamp endTime);

   
}
