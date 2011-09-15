/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PrivilegeDao;
import ff.dao.RoleDao;
import ff.dao.SysControllerDao;
import ff.model.Privilege;
import ff.model.Role;
import ff.model.SysController;
import ff.service.RoleService;
import ff.service.SysControllerService;
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
public class SysControllerServiceImpl implements SysControllerService {

    static Logger logger = Logger.getLogger(SysControllerServiceImpl.class.getName());
    private SysControllerDao SysControllerDao;

    public void setSysControllerDao(SysControllerDao SysControllerDao) {
        this.SysControllerDao = SysControllerDao;
    }
    
    
    @Override
    public String getAllSysControllers() {
        List<SysController> sysControllers = SysControllerDao.getAllSysControllers();
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray sysControllersJS = JSONArray.fromObject(sysControllers, jsonConfig);
        String jsonStr = "{totalProperty:" + sysControllers.size() + ",root:" + sysControllersJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public SysController getSysControllerById(Long id) {
        return SysControllerDao.getSysControllerById(id);
    }

    @Override
    public SysController saveOrUpdate(SysController sysController) {
        return SysControllerDao.saveOrUpdate(sysController);
    }
    
}
