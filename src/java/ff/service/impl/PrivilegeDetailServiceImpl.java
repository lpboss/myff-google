/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.PrivilegeDao;
import ff.dao.RoleDao;
import ff.dao.UserDao;
import ff.model.PrivilegeDetail;
import ff.model.Role;
import ff.model.User;
import ff.service.PrivilegeDetailService;
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
public class PrivilegeDetailServiceImpl implements PrivilegeDetailService {
    private PrivilegeDetail privilegeDetail;

    public void setPrivilegeDetail(PrivilegeDetail privilegeDetail) {
        this.privilegeDetail = privilegeDetail;
    }
    
    
    @Override
    public String getPrivilegeDetailsById(Long privilegeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
