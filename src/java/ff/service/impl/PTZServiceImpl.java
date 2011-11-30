/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PTZDao;
import ff.model.PTZ;
import ff.service.PTZService;
import ff.util.DateJsonValueProcessor;
import java.util.List;
import net.sf.json.JsonConfig;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author Jerry,2011-11-11
 */
public class PTZServiceImpl implements PTZService {

    private PTZDao ptzDao;

    //删除
    @Override
    public String deletePTZ(String id) {
        String info = "";
        ptzDao.deletePTZ(id);
        info = "success";
        String jsonStr = "{success:true,info:\"" + info + "\"}";
        return jsonStr;
    }

    public void setptzDao(PTZDao ptzDao) {
        this.ptzDao = ptzDao;
    }

    //修改
    @Override
    public String editPTZ(Long id) {
        PTZ ptz = ptzDao.getPTZById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{});                         //这是需要过滤掉的变量名。不过滤会引起循�        JSONObject monitorJS = JSONObject.fromObject(ptz, jsonConfig);
        JSONObject monitorJS = JSONObject.fromObject(ptz, jsonConfig);
        String jsonStr = monitorJS.toString();
        return jsonStr;
    }

    @Override
    public String updatePTZ(Long id, String name) {
        PTZ ptz = ptzDao.getPTZById(id);
        String info = null;
        if (ptz == null) {
            info = "没有该摄像机权限，不能编辑！";
        } else {
            PTZ monitorDB = ptzDao.getPTZByName(name);
            if (monitorDB != null && monitorDB.getId() != id) {
                info = "该摄像机权限编号已使用，请更换！";
            } else {
                ptz.setName(name);
                ptzDao.saveOrUpdate(ptz);             //存储对象
                info = "success";
            }
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //增加
    @Override
    public String create(PTZ ptz) {
        String info = null;
        if (ptzDao.getPTZByName(ptz.getName()) == null) {
            ptzDao.saveOrUpdate(ptz);
            info = "success";
        } else {
            info = "该用户名已使用，请更换！";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //得到所有的数据
    @Override
    public String getAllPTZsJSON() {
        List<PTZ> ptzs = ptzDao.getAllPTZs();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails", "fireAlarmDetails"});
        JSONArray rolesJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = "{totalProperty:" + ptzs.size() + ",root:" + rolesJS.toString() + "}";
        return jsonStr;
    }

    //得到数据列表
    @Override
    public String getPTZList() {
        List ptzs = ptzDao.getAllPTZs();
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名�        jsonConfig.setExcludes(new String[]{});
        jsonConfig.setExcludes(new String[]{"fireAlarmDetails", "rolePtzDetails"});
        JSONArray ptzJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = "{totalProperty:" + ptzs.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public PTZ saveOrUpdate(PTZ ptz) {
        return ptzDao.saveOrUpdate(ptz);
    }

    @Override
    public List<PTZ> getAllPTZs() {
        List ptzs = ptzDao.getAllPTZs();
        return ptzs;
    }

    @Override
    public String getPTZJSONById(Long id) {
        PTZ ptz = ptzDao.getPTZById(id);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"videos", "users"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONObject userJS = JSONObject.fromObject(ptz, jsonConfig);
        String jsonStr = userJS.toString();
        return jsonStr;
    }

    @Override
    public PTZ getPTZById(Long id) {
        System.out.println("bbbbbbbbbbbbbbbbbbb");
        
        return ptzDao.getPTZById(id);
    }

    @Override
    public String update(PTZ ptz) {
        String info = null;
        if (ptz == null) {
            info = "没有该用户，不能编辑";
        } else {
            PTZ userDB = ptzDao.getPTZByName(ptz.getName());
            if (userDB != null && userDB.getId() != ptz.getId()) {
                info = "该用户名已使用，请更换！";
            } else {
                ptzDao.saveOrUpdate(ptz);
                info = "success";
            }
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String getIsAlarmPTZsJSON() {
        List<PTZ> ptzs = ptzDao.getIsAlarmPTZs();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails"});
        JSONArray rolesJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = "{totalProperty:" + ptzs.size() + ",root:" + rolesJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public String ptzLock(PTZ ptz) {
        String info = null;
        ptzDao.saveOrUpdate(ptz);
        info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }
}
