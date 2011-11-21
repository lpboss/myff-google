/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.AlarmIgnoreAreasDao;
import ff.model.AlarmIgnoreAreas;
import ff.service.AlarmIgnoreAreasService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 *
 * @author Administrator
 */
public class AlarmIgnoreAreasServiceImpl implements AlarmIgnoreAreasService {

    private AlarmIgnoreAreasDao alarmIgnoreAreasDao;

    //得到数据列表
    @Override
    public String getAlarmIgnoreAreasList() {
        List alarmIgnoreAreases = alarmIgnoreAreasDao.getAlarmIgnoreAreases();
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        jsonConfig.setExcludes(new String[]{});
        JSONArray ptzJS = JSONArray.fromObject(alarmIgnoreAreases, jsonConfig);
        String jsonStr = "{totalProperty:" + alarmIgnoreAreases.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public AlarmIgnoreAreas saveOrUpdate(AlarmIgnoreAreas alarmIgnoreAreas) {
        return alarmIgnoreAreasDao.saveOrUpdate(alarmIgnoreAreas);
    }

    @Override
    public String getAlarmIgnoreAreasJSONById(Long id) {
        AlarmIgnoreAreas ptz = alarmIgnoreAreasDao.getAlarmIgnoreAreasById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"videos", "users"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONObject userJS = JSONObject.fromObject(ptz, jsonConfig);
        String jsonStr = userJS.toString();
        return jsonStr;
    }

    @Override
    public AlarmIgnoreAreas getAlarmIgnoreAreasById(Long id) {
        return alarmIgnoreAreasDao.getAlarmIgnoreAreasById(id);
    }

    @Override
    public String update(AlarmIgnoreAreas alarmIgnoreAreas) {
        String info = null;
        if (alarmIgnoreAreas == null) {
            info = "没有该用户，不能编辑！";
        } else {
            alarmIgnoreAreasDao.saveOrUpdate(alarmIgnoreAreas);
            info = "success";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }
}
