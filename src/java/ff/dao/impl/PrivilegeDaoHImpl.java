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
    public String getSysPrivilegeChildrenById(Long nodeId, Integer isLocked) {
        List<Privilege> privileges;
        if (isLocked == null) {
            privileges = this.getHibernateTemplate().findByNamedParam("from Privilege where parent_id=:parent_id order by sort_id", new String[]{"parent_id"}, new Object[]{nodeId});
        } else {
            privileges = this.getHibernateTemplate().findByNamedParam("from Privilege where parent_id=:parent_id AND is_locked=:is_locked order by sort_id", new String[]{"parent_id", "is_locked"}, new Object[]{nodeId, isLocked});
        }

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
        this.getHibernateTemplate().saveOrUpdate(privilege);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return privilege;
    }

    @Override
    public Privilege getPrivilegeByParentIdSortId(Long parentId, Integer sortId) {
        List<Privilege> privileges = this.getHibernateTemplate().findByNamedParam("from Privilege where parent_id =:parent_id AND sort_id =:sort_id", new String[]{"parent_id", "sort_id"}, new Object[]{parentId, sortId});
        if (privileges.size() > 0) {
            return privileges.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer getMaxSortIdByParentId(Long parentId) {
        HQLCallBackUtil callBack = new HQLCallBackUtil();
        callBack.setSql("select max(sort_id) as max from privileges where parent_id = " + parentId);
        List maxList = this.getHibernateTemplate().executeFind(callBack);
        return (Integer) maxList.get(0);
    }
}
