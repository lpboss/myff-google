/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.SysActionDao;
import ff.model.SysAction;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author jerry
 */
public class SysActionDaoHImpl extends HibernateDaoSupport implements SysActionDao {

    @Override
    public SysAction getSysActionById(Long id) {
        SysAction sysAction = (SysAction) this.getHibernateTemplate().get(SysAction.class, id);
        return sysAction;
    }

    @Override
    public List<SysAction> getAllSysActions(Long sysControllerId) {
        List<SysAction> sysActions = this.getHibernateTemplate().findByNamedParam(
                "from SysAction where sys_controller_id=:sys_controller_id", new String[]{"sys_controller_id"}, new Long[]{sysControllerId});
        return sysActions;
    }

    @Override
    public SysAction getSysActionByNameCId(String name, Long CId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
