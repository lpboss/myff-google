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
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails", "rolePtzDetails"});
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
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails", "fireAlarmDetails", "rolePtzDetails"});
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
        List<PTZ> ptzs;
        String ids = "";
        List<RolePtz> ignoreAreas = rolePtzSetDao.getById(id);

        for (int i = 0; i <= ignoreAreas.size() - 1; i++) {
            ids = ids + String.valueOf(ignoreAreas.get(i).getPtz().getId()) + ",";
        }
        String ptzids = ids.substring(0, ids.length() - 1);
        ptzs = rolePtzSetDao.getPtzsByIds(ptzids);


        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"RolePtzDetails", "fireAlarmDetails", "rolePtzDetails", "role"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        //    JSONObject userJS = JSONObject.fromObject(ignoreAreas, jsonConfig);
        JSONArray ignoreAreasJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = ignoreAreasJS.toString();
        return jsonStr;
    }

    //添加RolePtz
    @Override
    public String create(RolePtz rolePtz) {
        String info = null;
        rolePtzSetDao.saveOrUpdate(rolePtz);
        info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //删除
    @Override
    public String deleteRolePtz(String id, String roleid) {
        String info = "";
        rolePtzSetDao.deleteRolePtz(id, roleid);
        info = "success";
        String jsonStr = "{success:true,info:\"" + info + "\"}";
        return jsonStr;
    }

    //得到rolePtz列表
    @Override
    public String getRolePtzList() {
        System.out.println("frgfv");
        List rolePtzs = rolePtzSetDao.getAllRolePtzs();
         System.out.println("45gby");
          System.out.println(rolePtzs);
           System.out.println("nhn7");
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名�        jsonConfig.setExcludes(new String[]{});
        jsonConfig.setExcludes(new String[]{"fireAlarmDetails","rolePtzDetails","role"});
        JSONArray ptzJS = JSONArray.fromObject(rolePtzs, jsonConfig);
        String jsonStr = "{totalProperty:" + rolePtzs.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }
}
