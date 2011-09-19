/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.RolesPrivilegeDetailDao;
import ff.model.RolesPrivilegeDetail;
import java.sql.Timestamp;
import java.util.Calendar;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author jerry
 */
public class RolesPrivilegeDetailDaoHImpl extends HibernateDaoSupport implements RolesPrivilegeDetailDao {

    @Override
    public RolesPrivilegeDetail saveOrUpdate(RolesPrivilegeDetail rolesPrivilegeDetail) {
        if (rolesPrivilegeDetail.getId() == null) {
            rolesPrivilegeDetail.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        rolesPrivilegeDetail.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        this.getHibernateTemplate().saveOrUpdate(rolesPrivilegeDetail);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return rolesPrivilegeDetail;
    }

    @Override
    public void delete(RolesPrivilegeDetail rolesPrivilegeDetail) {
        this.getHibernateTemplate().delete(rolesPrivilegeDetail);
    }
}
