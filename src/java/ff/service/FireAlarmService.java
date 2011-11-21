/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.FireAlarm;

/**
 *
 * @author Administrator
 */
public interface FireAlarmService {

    String getFireAlarmList();

    String create(FireAlarm fireAlarm);

    String deleteFireAlarm(String id);

    String getFireAlarmJSONById(Long id);

    String update(FireAlarm fireAlarm);

    FireAlarm getFireAlarmById(Long id);
}
