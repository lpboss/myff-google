/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.service;

/**
 *
 * @author jerry
 */
public interface AdminService {

    Integer executeNativeUpdateSQL(String sql);

    String getRolePrivilegeDetailsById(Long roleId, Long privilegeId, Long parentPrivilegeId);
}
