/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

import ff.model.Role;
import ff.model.RolesPrivilegeDetail;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface RoleService {

    String createRole(String name);

    String deleteRole(Long id);

    String editRole(Long id);

    String updateRole(Long id, String name);

    String getAllRoles();
    
    //得到生成的角色菜单
    String getRoleAllMenus(Long roleId);
    
    Role getRoleById(Long id);
    
}
