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
public interface AdminDao {
    Integer executeNativeUpdateSQL(String sql);    
    List<RolesPrivilegeDetail> getRolesPrivilegeDetails(Long privilegeId,Long roleId);
}
