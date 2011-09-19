/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service.impl;

import ff.dao.AdminDao;
import ff.dao.PrivilegeDetailDao;
import ff.dao.RoleDao;
import ff.dao.RolesPrivilegeDetailDao;
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
    private RoleDao roleDao;
    private RolesPrivilegeDetailDao rolesPrivilegeDetailDao;

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public void setPrivilegeDetailDao(PrivilegeDetailDao privilegeDetailDao) {
        this.privilegeDetailDao = privilegeDetailDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
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

        if (sysPrivilegeDetails.size() > 0) {
            //如果此时角色还没有分配过权限细节，则把现有系统权限一次性追加上。
            if (rolePrivilegeDetails.size() == 0) {
                for (PrivilegeDetail sysPrivilegeDetail : sysPrivilegeDetails) {
                    RolesPrivilegeDetail rolesPrivilegeDetail = new RolesPrivilegeDetail();
                    rolesPrivilegeDetail.setMenuId(parentPrivilegeId);
                    rolesPrivilegeDetail.setMenuId(privilegeId);
                    rolesPrivilegeDetail.setPrivilegeDetailId(sysPrivilegeDetail.getId());
                    rolesPrivilegeDetail.setRole(roleDao.getRoleById(roleId));
                    rolesPrivilegeDetailDao.saveOrUpdate(rolesPrivilegeDetail);
                }
            } else {
            }
        }
        /*
         * if sysPrivilegeDetails != nil
        #如果此时角色还没有分配过权限细节，则把现有系统权限一次性追加上。
        if rolePrivilegeDetails.length == 0
        sysPrivilegeDetails.each do |sysPrivilegeDetail|
        rolesPrivilegeDetail = RolesPrivilegeDetail.new
        rolesPrivilegeDetail.company_id = session[:company_id]
        rolesPrivilegeDetail.module_id = params[:parent_privilege_id]
        rolesPrivilegeDetail.menu_id = params[:privilege_id]
        rolesPrivilegeDetail.privilege_detail_id  = sysPrivilegeDetail.id
        role.roles_privilege_details << rolesPrivilegeDetail
        end
        else
        #分二次循环，一是查是否有多的，第二次是查是滞有少的。
        #有不动，少则加，多则删
        #privilegeAction有三个值，1.add,2.remove,3.nil,""就是无动作
        privilegeAction = nil
        rolePrivilegeDetails.each do |rolePrivilegeDetail|
        privilegeAction = "remove"
        sysPrivilegeDetails.each do |sysPrivilegeDetail|
        if rolePrivilegeDetail.privilege_detail_id ==  sysPrivilegeDetail.id
        privilegeAction = nil
        end
        end
        if privilegeAction == "remove"
        rolePrivilegeDetails.delete(rolePrivilegeDetail)
        end
        end
        #第二次循环，就是添加
        privilegeAction = nil
        sysPrivilegeDetails.each do |sysPrivilegeDetail|
        privilegeAction = "add"
        rolePrivilegeDetails.each do |rolePrivilegeDetail|
        if rolePrivilegeDetail.privilege_detail_id ==  sysPrivilegeDetail.id
        privilegeAction = nil
        end
        end
        if privilegeAction == "add"
        rolesPrivilegeDetail = RolesPrivilegeDetail.new
        rolesPrivilegeDetail.company_id = session[:company_id]
        rolesPrivilegeDetail.module_id = params[:parent_privilege_id]
        rolesPrivilegeDetail.menu_id = params[:privilege_id]
        rolesPrivilegeDetail.privilege_detail_id  = sysPrivilegeDetail.id
        role.roles_privilege_details << rolesPrivilegeDetail
        end
        end
        end      
        end
         */

        return null;
    }
}
