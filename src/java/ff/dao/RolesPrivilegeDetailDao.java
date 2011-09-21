/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.RolesPrivilegeDetail;
import java.util.List;

/**
 *
 * @author jerry
 */
public interface RolesPrivilegeDetailDao {

    RolesPrivilegeDetail saveOrUpdate(RolesPrivilegeDetail rolesPrivilegeDetail);

    void delete(RolesPrivilegeDetail rolesPrivilegeDetail);

    RolesPrivilegeDetail getRolesPrivilegeDetailById(Long id);

    //在privilege方法删除后，配合此方法，删除所有的角色权限细节。
    String deleteForSysPrivilegeDelete(Long privilegeId);
    
    //在privilegeDetail方法删除后，配合此方法，删除所有的角色权限细节。
    String deleteForSysPrivilegeDetailDelete(Long privilegeDetailId);
}
