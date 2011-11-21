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
    public String getFireAlarmList(Integer ptzId) {
        List fireAlarms;
        if (ptzId == null) {
            fireAlarms = fireAlarmDao.getAllFireAlarms();
        }else{
            fireAlarms =fireAlarmDao.getFireAlarmsByPtzId(ptzId);
        }

        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        jsonConfig.setExcludes(new String[]{"videos", "users", "user", "rolesPrivilegeDetails"});

        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONArray fireAlarmJS = JSONArray.fromObject(fireAlarms, jsonConfig);
        String jsonStr = "{totalProperty:" + fireAlarms.size() + ",root:" + fireAlarmJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public String create(FireAlarm fireAlarm) {
        String info = null;

        fireAlarmDao.saveOrUpdate(fireAlarm);
        info = "success";

        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String deleteFireAlarm(String id) {
        String info = null;



        fireAlarmDao.delFireAlarmAll(id);




        info = "success";


        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String getFireAlarmJSONById(Long id) {
        System.out.println("bbbbbbbbbbbb");
        FireAlarm user = fireAlarmDao.getFireAlarmById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"videos", "users"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject userJS = JSONObject.fromObject(user, jsonConfig);
        String jsonStr = userJS.toString();
        return jsonStr;
    }

    @Override
    public String update(FireAlarm fireAlarm) {
        String info = null;
        if (fireAlarm == null) {
            info = "没有该用户，不能编辑！";
        } else {

            fireAlarmDao.saveOrUpdate(fireAlarm);
            info = "success";

        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public FireAlarm getFireAlarmById(Long id) {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb");
        return fireAlarmDao.getFireAlarmById(id);
    }
}
