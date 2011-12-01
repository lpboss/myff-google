/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.SysActionDao;
import ff.model.SysAction;
import ff.service.SysActionService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author Joey
 */
public class SysActionServiceImpl implements SysActionService {

    static Logger logger = Logger.getLogger(SysActionServiceImpl.class.getName());
    private SysActionDao sysActionDao;

    public void setSysActionDao(SysActionDao sysActionDao) {
        this.sysActionDao = sysActionDao;
    }
    
    
    @Override
    public String getAllSysActions(Long sysActionId) {
        List<SysAction> sysActions = sysActionDao.getAllSysActions(sysActionId);
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray sysActionsJS = JSONArray.fromObject(sysActions, jsonConfig);
        String jsonStr = "{totalProperty:" + sysActions.size() + ",root:" + sysActionsJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public SysAction getSysActionById(Long id) {
        return sysActionDao.getSysActionById(id);
    }

    @Override
    public SysAction saveOrUpdate(SysAction sysAction) {
        return sysActionDao.saveOrUpdate(sysAction);
    }
    
}
