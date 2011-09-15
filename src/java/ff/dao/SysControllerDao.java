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
    
    /**
     * 根据控制器名得到控制器
     * @param name
     * @return 
     */
    SysController getSysControllerByName(String name);
    
    SysController saveOrUpdate(SysController sysController);
}
