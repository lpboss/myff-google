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

    //因为有写入操作，方便事务的情况叫，叫do
    String doRolePrivilegeDetailsById(Long roleId, Long privilegeId, Long parentPrivilegeId);
    
    
}
