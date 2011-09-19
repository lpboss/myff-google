/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.PrivilegeDetail;
import java.util.List;

/**
 *
 * @author Jerry
 */
public interface PrivilegeDetailDao {
    
     List<PrivilegeDetail> getPrivilegeDetailsById(Long privilegeId);
     
     List<PrivilegeDetail> getUnlockedSysPrivilegeDetails(Long privilegeId);
     
     PrivilegeDetail saveOrUpdate(PrivilegeDetail privilegeDetail);
     
     PrivilegeDetail getPrivilegeDetailById(Long id);
     
}
