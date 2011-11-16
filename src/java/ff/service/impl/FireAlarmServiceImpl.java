/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.FireAlarmDao;
import ff.model.FireAlarm;

import ff.service.FireAlarmService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class FireAlarmServiceImpl implements FireAlarmService {

    private FireAlarmDao fireAlarmDao;

    public FireAlarmDao getFireAlarmDao() {
        return fireAlarmDao;
    }

    public void setFireAlarmDao(FireAlarmDao fireAlarmDao) {
        this.fireAlarmDao = fireAlarmDao;
    }

    @Override
    public String getFireAlarmList() {

        List fireAlarms = fireAlarmDao.getAllFireAlarms();

        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        jsonConfig.setExcludes(new String[]{"videos", "users", "user", "rolesPrivilegeDetails"});

        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray fireAlarmJS = JSONArray.fromObject(fireAlarms, jsonConfig);
        String jsonStr = "{totalProperty:" + fireAlarms.size() + ",root:" + fireAlarmJS.toString() + "}";
        return jsonStr;
    }
}
