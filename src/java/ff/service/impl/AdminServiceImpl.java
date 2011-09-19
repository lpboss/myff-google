/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.AdminDao;
import ff.service.AdminService;

/**
 *
 * @author jerry
 */
public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao;

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Integer executeNativeUpdateSQL(String sql) {
        return adminDao.executeNativeUpdateSQL(sql);
    }

    @Override
    public String getRolePrivilegeDetailsById(Long roleId, Long privilegeId, Long parentPrivilegeId) {
        
        
        return null;
    }
}
