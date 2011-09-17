/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.PrivilegeDao;
import ff.dao.PrivilegeDetailDao;
import ff.dao.RoleDao;
import ff.model.Privilege;
import ff.model.PrivilegeDetail;
import ff.model.Role;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Jerry
 */
public class PrivilegeDetailDaoHImpl extends HibernateDaoSupport implements PrivilegeDetailDao {

    @Override
    public List<PrivilegeDetail> getPrivilegeDetailsById(Long privilegeId) {
        List<PrivilegeDetail> privilegeDetails = this.getHibernateTemplate().findByNamedParam("from PrivilegeDetail where privilegeId =:privilege_id", new String[]{"privilege_id"}, new Long[]{privilegeId});
        return privilegeDetails;
    }

    @Override
    public PrivilegeDetail saveOrUpdate(PrivilegeDetail privilegeDetail) {
        if (privilegeDetail.getId() == null) {
            privilegeDetail.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        privilegeDetail.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        this.getHibernateTemplate().saveOrUpdate(privilegeDetail);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return privilegeDetail;
    }

    @Override
    public PrivilegeDetail getPrivilegeDetailById(Long id) {
        PrivilegeDetail privilegeDetail = (PrivilegeDetail) this.getHibernateTemplate().get(PrivilegeDetail.class, id);
        return privilegeDetail;
    }
}
