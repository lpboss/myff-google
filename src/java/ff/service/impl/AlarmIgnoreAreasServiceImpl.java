/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.AlarmIgnoreAreasDao;
import ff.model.AlarmIgnoreAreas;
import ff.service.AlarmIgnoreAreasService;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 *
 * @author Administrator
 */
public class AlarmIgnoreAreasServiceImpl implements AlarmIgnoreAreasService{
    
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

  
    
}
