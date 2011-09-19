/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.AdminDao;
import ff.dao.PrivilegeDetailDao;
import ff.model.PrivilegeDetail;
import ff.model.RolesPrivilegeDetail;
import ff.service.AdminService;
import java.util.List;

/**
 *
 * @author jerry
 */
public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao;
    private PrivilegeDetailDao privilegeDetailDao;

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Integer executeNativeUpdateSQL(String sql) {
        return adminDao.executeNativeUpdateSQL(sql);
    }

    @Override
    public String getRolePrivilegeDetailsById(Long roleId, Long privilegeId, Long parentPrivilegeId) {
       //在这里，角色中的privilege已经深化为menu_id，是一系列相关权限细节的集合
       //首先得到系统中，此角色当前已经对应的角色权限细节
       List<RolesPrivilegeDetail> rolePrivilegeDetails = adminDao.getRolesPrivilegeDetails(privilegeId, roleId);
       //得到，所有非锁定的系统权限
       List<PrivilegeDetail> sysPrivilegeDetails = privilegeDetailDao.getUnlockedSysPrivilegeDetails(privilegeId);
       
       
        return null;
    }
}
