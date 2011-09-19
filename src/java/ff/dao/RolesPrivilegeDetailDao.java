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
}
