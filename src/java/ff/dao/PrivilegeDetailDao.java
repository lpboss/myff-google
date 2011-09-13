/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.PrivilegeDetail;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface PrivilegeDetailDao {
    
     List<PrivilegeDetail> getPrivilegeDetailsById(Long privilegeId);
}
