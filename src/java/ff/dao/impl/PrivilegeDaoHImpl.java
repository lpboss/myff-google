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
 * @author Jerry
 */
public class PrivilegeDaoHImpl extends HibernateDaoSupport implements PrivilegeDao {

    @Override
    public Privilege getPrivilegeById(Long id) {
        Privilege privilege = (Privilege) this.getHibernateTemplate().get(Privilege.class, id);
        return privilege;
    }

    @Override
    public String getSysPrivilegeChildrenById(Long nodeId) {
        List<Privilege> privileges = this.getHibernateTemplate().findByNamedParam("from Privilege where parent_id=:parent_id", new String[]{"parent_id"}, new Long[]{nodeId});
        String treeData = "";
        if (privileges.size() > 0) {
            treeData = "[";
            for (Privilege privilege : privileges) {
                treeData += "{id:" + privilege.getId() + ",text:\"" + privilege.getName() + "\"";
                if (privilege.getLevel() == 0) {
                    treeData += ",leaf:false";
                } else {
                    treeData += ",leaf:true";
                }

                if (privilege.getIsLocked() == 1) {
                    treeData += ",checked:true},";
                } else {
                    treeData += ",checked:false},";
                }
            }
            treeData = treeData.substring(0, treeData.length() - 1);
            treeData += "]";
        }
        return treeData;
    }

    @Override
    public List<Privilege> getAllModules() {
        List<Privilege> privileges = this.getHibernateTemplate().findByNamedParam("from Privilege where parent_id =:parent_id", new String[]{"parent_id"}, new Object[]{0});
        return privileges;
    }

    @Override
    public Privilege saveOrUpdate(Privilege privilege) {
        if (privilege.getId() == null) {
            privilege.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        privilege.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        this.getHibernateTemplate().save(privilege);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return privilege;
    }
}
