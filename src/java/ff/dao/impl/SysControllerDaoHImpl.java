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
        SysController sysController = (SysController) this.getHibernateTemplate().get(SysController.class, id);
        return sysController;
    }

    @Override
    public List<SysController> getAllSysControllers() {
        List<SysController> sysControllers = this.getHibernateTemplate().find("from SysController");
        return sysControllers;
    }

    @Override
    public SysController getSysControllerByName(String name) {
        List<SysController> sysControllers = this.getHibernateTemplate().findByNamedParam(
                "from SysController where name=:name", new String[]{"name"},
                new String[]{name});
        if (sysControllers.size() > 0) {
            return sysControllers.get(0);
        } else {
            return null;
        }
    }
    
}
