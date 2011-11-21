/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.FireAlarm;
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
}
