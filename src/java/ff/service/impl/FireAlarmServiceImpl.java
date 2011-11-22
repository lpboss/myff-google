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
//得到火警信息

    @Override
    public String getFireAlarmList(Integer ptzId, Timestamp beginTime, Timestamp endTime) {
        List fireAlarms = null;

        // 云台ID 不为空
        if (ptzId != null && beginTime == null && endTime == null) {
            fireAlarms = fireAlarmDao.getFireAlarmsByPtzId(ptzId);
        }
        // 开始时间  不为空
        if (beginTime != null && ptzId == null && endTime == null) {
            fireAlarms = fireAlarmDao.getFireAlarmsByBeginTime(beginTime);
        }
        // 结束时间  不为空
        if (endTime != null && beginTime == null && ptzId == null) {
            fireAlarms = fireAlarmDao.getFireAlarmsByEndTime(endTime);
        }
        // 开始时间   结束时间 
        if (endTime != null && beginTime != null && ptzId == null) {
            fireAlarms = fireAlarmDao.getFireAlarmsByEndTimeBeginTime(endTime, beginTime);
        }
        //结束时间    云台ID
        if (endTime != null && beginTime == null && ptzId != null) {
            fireAlarms = fireAlarmDao.getFireAlarmsByEndTimePtzId(ptzId, endTime);
        }
        // 开始时间  云台ID
        if (endTime == null && beginTime != null && ptzId != null) {
            fireAlarms = fireAlarmDao.getFireAlarmsByBeginTimePtzId(ptzId, beginTime);
        }
        // 开始时间   结束时间   云台ID
        if (endTime != null && beginTime != null && ptzId != null) {
            fireAlarms = fireAlarmDao.getFireAlarmsByBeginTimePtzId(ptzId, beginTime, endTime);
        }
        //   都为空
        if (ptzId == null && beginTime == null && endTime == null) {
            fireAlarms = fireAlarmDao.getAllFireAlarms();
      
        }
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        jsonConfig.setExcludes(new String[]{"videos", "users", "user", "rolesPrivilegeDetails"});

        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONArray fireAlarmJS = JSONArray.fromObject(fireAlarms, jsonConfig);
        String jsonStr = "{totalProperty:" + fireAlarms.size() + ",root:" + fireAlarmJS.toString() + "}";
        return jsonStr;
    }
//创建

    @Override
    public String create(FireAlarm fireAlarm) {
        String info = null;

        fireAlarmDao.saveOrUpdate(fireAlarm);
        info = "success";

        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }
//删除

    @Override
    public String deleteFireAlarm(String id) {
        String info = null;



        fireAlarmDao.delFireAlarmAll(id);




        info = "success";


        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }
//得到某一个火警信息JSON

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
//更新

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
//得到某一个记录

    @Override
    public FireAlarm getFireAlarmById(Long id) {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb");
        return fireAlarmDao.getFireAlarmById(id);
    }
}
