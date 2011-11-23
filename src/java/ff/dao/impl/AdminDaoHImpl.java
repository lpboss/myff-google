/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.AdminDao;
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
public class AdminDaoHImpl extends HibernateDaoSupport implements AdminDao {

    @Override
    public Integer executeNativeUpdateSQL(String sql) {
        HQLCallBackUtil callBack = new HQLCallBackUtil();
        callBack.setSql(sql);
        List list = this.getHibernateTemplate().executeFind(callBack);
        return (Integer) list.size();
    }

    @Override
    public List<RolesPrivilegeDetail> getRolesPrivilegeDetails(Long privilegeId, Long roleId) {
        List<RolesPrivilegeDetail> rolesPrivilegeDetails = this.getHibernateTemplate().findByNamedParam(
                "from RolesPrivilegeDetail where menu_id =:menu_id AND role_id =:role_id", new String[]{"menu_id", "role_id"},new Object[]{privilegeId, roleId});
        return rolesPrivilegeDetails;
    }
}
