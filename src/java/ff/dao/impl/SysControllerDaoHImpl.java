/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.SysControllerDao;
import ff.model.SysController;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author jerry
 */
public class SysControllerDaoHImpl extends HibernateDaoSupport implements SysControllerDao {

    @Override
    public SysController getSysControllerById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SysController> getAllSysControllers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
