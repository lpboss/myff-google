/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PrivilegeDao;
import ff.dao.RoleDao;
import ff.dao.UserDao;
import ff.model.Role;
import ff.model.User;
import ff.service.PrivilegeService;
import ff.service.UserService;
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
}
