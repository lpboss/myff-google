/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PTZDao;
import ff.model.PTZ;
import ff.service.PTZService;
import java.util.List;
import net.sf.json.JsonConfig;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

/**
 *
 * @author jerry
 */
public class PTZServiceImpl implements PTZService {

    private PTZDao ptzDao;

    //删除
    @Override
    public String deletePTZ(Long id) {
        String info = ptzDao.deletePTZ(id);
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
        jsonConfig.setExcludes(new String[]{});                         //这是需要过滤掉的变量名。不过滤会引起循环
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
    public String createPTZ(String name) {
        PTZ ptz = new PTZ();
        String info = null;
        if (ptzDao.getPTZByName(name) == null) {
            ptz.setName(name);
            ptzDao.saveOrUpdate(ptz);                               //存储对象
            info = "success";
        } else {
            info = "该云台名已使用，请更换！";
        }
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    //得到所有的数据
    @Override
    public String getAllPTZsJSON() {
        List<PTZ> ptzs = ptzDao.getAllPTZs();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users", "rolesPrivilegeDetails"});
        JSONArray rolesJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = "{totalProperty:" + ptzs.size() + ",root:" + rolesJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public String getPTZList() {
        List ptzs = ptzDao.getAllPTZs();
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        jsonConfig.setExcludes(new String[]{});
        JSONArray ptzJS = JSONArray.fromObject(ptzs, jsonConfig);
        String jsonStr = "{totalProperty:" + ptzs.size() + ",root:" + ptzJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public List<PTZ> getAllPTZs() {
        List ptzs = ptzDao.getAllPTZs();
        return ptzs;
    }
}
