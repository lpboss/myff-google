/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PTZDao;
import ff.dao.RolePtzDao;
import ff.model.PTZ;
import ff.model.Role;
import ff.model.RolePtz;
import ff.service.RolePtzService;
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
public class RolePtzServiceImpl implements RolePtzService {

    private RolePtzDao rolePtzDao;
    private PTZDao ptzDao;

    public void setPtzDao(PTZDao ptzDao) {
        this.ptzDao = ptzDao;
    }

    public RolePtzDao getRolePtzDao() {
        return rolePtzDao;
    }

    public void setRolePtzDao(RolePtzDao rolePtzDao) {
        this.rolePtzDao = rolePtzDao;
    }

    //得到所有角色
    @Override
    public String getAllRoles() {
        List<Role> roles = rolePtzDao.getAllRoles();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails", "rolePtz","fireAlarm"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray rolesJS = JSONArray.fromObject(roles, jsonConfig);
        String jsonStr = "{totalProperty:" + roles.size() + ",root:" + rolesJS.toString() + "}";
        return jsonStr;
    }

    //得到所有ptz
    @Override
    public String getPTZList() {
        List ptzs = rolePtzDao.getAllPTZs();

        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名�        jsonConfig.setExcludes(new String[]{});
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails", "fireAlarm", "rolePtz"});
        JSONArray ptzJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = "{totalProperty:" + ptzs.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }

    //得到某一条rolePtz数据
    @Override
    public RolePtz getRolePtzById(Long id,Long roleid) {
     RolePtz rolePtz = rolePtzDao.getRolePtzById(id,roleid);
      return  rolePtz;
    }

    //是否锁定
    @Override
    public String RolePtzDefault(RolePtz rolePtz) {
        String info = null;

        rolePtzDao.saveOrUpdate(rolePtz);
        info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //处理是否是默认云台
    @Override
    public String resetDefault(Long id) {
        String info = null;
        rolePtzDao.setDefault(id);
        info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //得到某一条数据
    @Override
    public String getRolePtzJSONById(Integer id) {
        List<PTZ> ptzs;
        List<RolePtz> rolePtzs;
        String ids = "";
        List<RolePtz> ignoreAreas = rolePtzDao.getById(id);

//        for (int i = 0; i <= ignoreAreas.size() - 1; i++) {
//            ids = ids + String.valueOf(ignoreAreas.get(i).getPtz().getId()) + ",";
//        }
//        String ptzids = ids.substring(0, ids.length() - 1);
//        // ptzs = rolePtzDao.getPtzsByIds(ptzids);
//        rolePtzs = rolePtzDao.getRolePtzByIds(ptzids);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fireAlarm", "rolePtz", "rolesPrivilegeDetails"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        //    JSONObject userJS = JSONObject.fromObject(ignoreAreas, jsonConfig);
        JSONArray ignoreAreasJS = JSONArray.fromObject(ignoreAreas, jsonConfig);
        String jsonStr = ignoreAreasJS.toString();
        return jsonStr;
    }

    //添加RolePtz
    @Override
    public String create(RolePtz rolePtz,Long ptzId,Long roleId) {
        String info = null;

        if (rolePtzDao.getRolePtzByName(ptzId, roleId) == null) {
            rolePtzDao.saveOrUpdate(rolePtz);                               //存储对象
            info = "success";
        } else {
            info = "该角色名所控制的云台已经存在，请更换！";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //删除
    @Override
    public String deleteRolePtz(String id, String roleid) {
        String info = "";
        rolePtzDao.deleteRolePtz(id, roleid);
        info = "success";
        String jsonStr = "{success:true,info:\"" + info + "\"}";
        return jsonStr;
    }

    //得到rolePtz列表
    @Override
    public String getRolePtzList() {
        System.out.println("frgfv");
        List rolePtzs = rolePtzDao.getAllRolePtzs();
        System.out.println("45gby");
        System.out.println(rolePtzs);
        System.out.println("nhn7");
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名�        jsonConfig.setExcludes(new String[]{});
        jsonConfig.setExcludes(new String[]{"fireAlarm", "rolePtz", "rolesPrivilegeDetails"});
        JSONArray ptzJS = JSONArray.fromObject(rolePtzs, jsonConfig);
        String jsonStr = "{totalProperty:" + rolePtzs.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }

    //通过id得到rolePtz列表
    @Override
    public String getRolePtzById(Long id) {
        List<PTZ> ptzs;
        List<RolePtz> rolePtzs;
        String ids = "";
        System.out.println("111111111111111111111111111111111");
        RolePtz ignoreAreas = rolePtzDao.getRolePtzsById(id);
        System.out.println("14fc432v2");
        System.out.println(id);
        System.out.println(ignoreAreas);
        System.out.println("14fc432v2");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fireAlarm", "rolePtz", "rolesPrivilegeDetails"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONArray ignoreAreasJS = JSONArray.fromObject(ignoreAreas, jsonConfig);
        String jsonStr = ignoreAreasJS.toString();
        return jsonStr;
    }
    
    
}
