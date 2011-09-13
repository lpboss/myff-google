/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.PrivilegeDao;
import ff.dao.RoleDao;
import ff.model.Privilege;
import ff.model.Role;
import ff.model.RolesPrivilegeDetail;
import ff.util.HQLCallBackUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Joey
 */
public class PrivilegeDaoHImpl extends HibernateDaoSupport implements PrivilegeDao {

    @Override
    public Privilege getPrivilegeById(Long id) {
        Privilege privilege = (Privilege) this.getHibernateTemplate().get(Privilege.class, id);
        return privilege;
    }
}
