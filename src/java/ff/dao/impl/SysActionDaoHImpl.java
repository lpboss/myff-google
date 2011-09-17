/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.SysActionDao;
import ff.model.SysAction;

import java.sql.Timestamp;
import java.util.Calendar;
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
        List<SysAction> sysActions = this.getHibernateTemplate().findByNamedParam(
                "from SysAction where name=:name AND sys_controller_id=:sys_controller_id", new String[]{"name","sys_controller_id"},
                new String[]{name,String.valueOf(CId)});
        if (sysActions.size() > 0) {
            return sysActions.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SysAction saveOrUpdate(SysAction sysAction) {
        //以下二句可写在其它部分，这里只是提示一下如何生成TimeStamp
        if (sysAction.getId() == null) {
            sysAction.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        sysAction.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        this.getHibernateTemplate().saveOrUpdate(sysAction);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return sysAction;
    }
}
