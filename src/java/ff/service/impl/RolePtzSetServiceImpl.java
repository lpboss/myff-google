/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PTZDao;
import ff.dao.RolePtzSetDao;
import ff.model.PTZ;
import ff.model.Role;
import ff.model.RolePtz;
import ff.service.RolePtzSetService;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JsonConfig;
import ff.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class RolePtzSetServiceImpl implements RolePtzSetService {

    private RolePtzSetDao rolePtzSetDao;
    private PTZDao ptzDao;

    public void setPtzDao(PTZDao ptzDao) {
        this.ptzDao = ptzDao;
    }

    public RolePtzSetDao getRolePtzSetDao() {
        return rolePtzSetDao;
    }

    public void setRolePtzSetDao(RolePtzSetDao rolePtzSetDao) {
        this.rolePtzSetDao = rolePtzSetDao;
    }

    //得到所有角色
    @Override
    public String getAllRoles() {
        List<Role> roles = rolePtzSetDao.getAllRoles();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray rolesJS = JSONArray.fromObject(roles, jsonConfig);
        String jsonStr = "{totalProperty:" + roles.size() + ",root:" + rolesJS.toString() + "}";
        return jsonStr;
    }

    //得到所有ptz
    @Override
    public String getPTZList() {
        List ptzs = rolePtzSetDao.getAllPTZs();

        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名�        jsonConfig.setExcludes(new String[]{});
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails", "fireAlarmDetails"});
        JSONArray ptzJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = "{totalProperty:" + ptzs.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }

    //得到某一条ptz数据
    @Override
    public PTZ getPTZById(Long id) {
        return rolePtzSetDao.getPTZById(id);
    }

    //是否锁定
    @Override
    public String ptzLock(PTZ ptz) {
        String info = null;

        ptzDao.saveOrUpdate(ptz);
        info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String resetDefault(Long id) {
        String info = null;
        rolePtzSetDao.setDefault(id);
        info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //得到某一条数据
    @Override
    public String getRolePtzSetJSONById(Integer id) {
        System.out.print("12121");
        System.out.print(id);
        String ids ="";
        List<RolePtz> ignoreAreas = rolePtzSetDao.getById(id);
        for (int i = 0; i <= ignoreAreas.size() - 1; i++) {
            ids = ids + String.valueOf(ignoreAreas.get(i).getPtz().getId())+",";

        }
        
        System.out.println("345");
        System.out.println(ignoreAreas.get(0).getId());
        System.out.println(ignoreAreas.get(1).getId());
        System.out.println(ignoreAreas.get(2).getId());
        System.out.println("678");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users", "fireAlarmDetails"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        //    JSONObject userJS = JSONObject.fromObject(ignoreAreas, jsonConfig);
        JSONArray ignoreAreasJS = JSONArray.fromObject(ignoreAreas, jsonConfig);
        String jsonStr = ignoreAreasJS.toString();
        return jsonStr;
    }
}
