/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.RolesPrivilegeDetailDao;
import ff.model.RolesPrivilegeDetail;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
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

    @Override
    public RolesPrivilegeDetail getRolesPrivilegeDetailById(Long id) {
        RolesPrivilegeDetail rolesPrivilegeDetail = (RolesPrivilegeDetail) this.getHibernateTemplate().get(RolesPrivilegeDetail.class, id);
        return rolesPrivilegeDetail;
    }

    @Override
    public String deleteForSysPrivilegeDelete(Long privilegeId) {
        List<RolesPrivilegeDetail> rolesPrivilegeDetails = this.getHibernateTemplate().findByNamedParam("from RolesPrivilegeDetail where module_id =:privilege_id OR menu_id =:privilege_id", new String[]{"privilege_id"}, new Long[]{privilegeId});
        if (rolesPrivilegeDetails.size() > 0) {
            this.getHibernateTemplate().deleteAll(rolesPrivilegeDetails);
        }
        return "success";
    }

    @Override
    public String deleteForSysPrivilegeDetailDelete(Long privilegeDetailId) {
        List<RolesPrivilegeDetail> rolesPrivilegeDetails = this.getHibernateTemplate().findByNamedParam("from RolesPrivilegeDetail where privilege_detail_id =:privilege__detail_id", new String[]{"privilege__detail_id"}, new Long[]{privilegeDetailId});
        if (rolesPrivilegeDetails.size() > 0) {
            this.getHibernateTemplate().deleteAll(rolesPrivilegeDetails);
        }
        return "success";
    }
}
