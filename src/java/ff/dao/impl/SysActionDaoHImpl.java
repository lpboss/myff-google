/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.SysActionDao;
import ff.model.SysAction;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author jerry
 */
public class SysActionDaoHImpl extends HibernateDaoSupport implements SysActionDao {

    @Override
    public SysAction getSysActionById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
