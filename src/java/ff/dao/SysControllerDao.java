/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao;

import ff.model.SysController;
import java.util.List;

/**
 *
 * @author Joey
 */
public interface SysControllerDao {

    List<SysController> getAllSysControllers();
    SysController getSysControllerById(Long id);
    
}
