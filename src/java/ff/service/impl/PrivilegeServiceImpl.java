/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PrivilegeDao;

import ff.model.Privilege;
import ff.model.PrivilegeDetail;
import ff.service.PrivilegeService;
import ff.util.DateJsonValueProcessor;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

/**
 *
 * @author jerry
 */
public class PrivilegeServiceImpl implements PrivilegeService {

    static Logger logger = Logger.getLogger(PrivilegeServiceImpl.class.getName());
    //注入区：
    private PrivilegeDao privilegeDao;

    public void setPrivilegeDao(PrivilegeDao privilegeDao) {
        this.privilegeDao = privilegeDao;
    }

    @Override
    public String getSysPrivilegeChildrenById(Long nodeId) {
        return privilegeDao.getSysPrivilegeChildrenById(nodeId);
    }

    @Override
    public Privilege getPrivilegeById(Long id) {
        return privilegeDao.getPrivilegeById(id);
    }

    @Override
    public String getPrivilegeJSONById(Long id) {
        Privilege privilege = privilegeDao.getPrivilegeById(id);
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray privilegeJS = JSONArray.fromObject(privilege, jsonConfig);
        String jsonStr = "{root:" + privilegeJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public String getAllModulesJSON() {
        List<Privilege> privileges = privilegeDao.getAllModules();
        JsonConfig jsonConfig = new JsonConfig();
        //这是需要过滤掉的变量名。
        //jsonConfig.setExcludes(new String[]{"videos", "users", "role_monitors"});
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
        JSONArray privilegesJS = JSONArray.fromObject(privileges, jsonConfig);
        String jsonStr = "{totalProperty:" + privileges.size() + ",root:" + privilegesJS.toString() + "}";
        return jsonStr;
    }

    @Override
    public Privilege saveOrUpdate(Privilege privilege) {
        return privilegeDao.saveOrUpdate(privilege);
    }

    @Override
    public Privilege getPrivilegeByParentIdSortId(Long parentId, Integer sortId) {
        return privilegeDao.getPrivilegeByParentIdSortId(parentId, sortId);
    }

    @Override
    public Integer getMaxSortIdByParentId(Long parentId) {
        return privilegeDao.getMaxSortIdByParentId(parentId);
    }

}
