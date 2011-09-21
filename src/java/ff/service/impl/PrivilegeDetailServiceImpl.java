/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PrivilegeDao;
import ff.dao.PrivilegeDetailDao;
import ff.dao.RolesPrivilegeDetailDao;
import ff.model.PrivilegeDetail;
import ff.service.PrivilegeDetailService;
import ff.service.PrivilegeService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author jerry
 */
public class PrivilegeDetailServiceImpl implements PrivilegeDetailService {

    private PrivilegeDetail privilegeDetail;
    private PrivilegeDetailDao privilegeDetailDao;
    private RolesPrivilegeDetailDao rolesPrivilegeDetailDao;

    public void setPrivilegeDetail(PrivilegeDetail privilegeDetail) {
        this.privilegeDetail = privilegeDetail;
    }

    public void setPrivilegeDetailDao(PrivilegeDetailDao privilegeDetailDao) {
        this.privilegeDetailDao = privilegeDetailDao;
    }

    @Override
    public String getPrivilegeDetailsById(Long privilegeId) {
        List<PrivilegeDetail> privilegeDetails = privilegeDetailDao.getPrivilegeDetailsById(privilegeId);
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray privilegeDetailsJS = JSONArray.fromObject(privilegeDetails, jsonConfig);
        String jsonStr = "{totalProperty:" + privilegeDetails.size() + ",root:" + privilegeDetailsJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public String create(PrivilegeDetail privilegeDetail) {
        privilegeDetailDao.saveOrUpdate(privilegeDetail);
        String info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public PrivilegeDetail getPrivilegeDetailById(Long id) {
        return privilegeDetailDao.getPrivilegeDetailById(id);
    }

    @Override
    public String getPrivilegeDetailJSONById(Long id) {
        PrivilegeDetail privilegeDetail = privilegeDetailDao.getPrivilegeDetailById(id);
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray privilegeDetailJS = JSONArray.fromObject(privilegeDetail, jsonConfig);
        String jsonStr = "{root:" + privilegeDetailJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public String update(PrivilegeDetail privilegeDetail) {
        privilegeDetailDao.saveOrUpdate(privilegeDetail);
        String info = "success";
        String jsonStr = "{success:true,info:'" + info + "'}";
        return jsonStr;
    }

    @Override
    public String delete(Long id) {
        privilegeDetailDao.delete(id);
        rolesPrivilegeDetailDao.deleteForSysPrivilegeDetailDelete(id);
        return "success";
    }
}
