/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.dao.impl;

import ff.dao.RoleDao;
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
public class RoleDaoHImpl extends HibernateDaoSupport implements RoleDao {

    @Override
    public Role saveOrUpdate(Role role) {
        if (role.getId() == null) {
            role.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        }
        role.setUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));

        //正式开始存储数据
        this.getHibernateTemplate().saveOrUpdate(role);
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().clear();
        return role;
    }

    @Override
    public String deleteRole(Long id) {
        try {
            Object role = this.getHibernateTemplate().load(Role.class, new Long(id));    //先加载特定实例
            getHibernateTemplate().delete(role);                                 //删除特定实例
        } catch (Exception e) {
            return e.toString();
        }
        return "success";
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = (Role) this.getHibernateTemplate().get(Role.class, id);
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = this.getHibernateTemplate().find("from Role");
        return roles;
    }

    @Override
    public Role getRoleByName(String name) {
        List<Role> roles = this.getHibernateTemplate().findByNamedParam("from Role where name=:name", new String[]{"name"}, new String[]{name});
        if (roles.size() > 0) {
            return roles.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Object[]> getRoleModules(Long roleId) {
        HQLCallBackUtil callBack = new HQLCallBackUtil();
        callBack.setSql("select DISTINCT rpd.module_id,p.name from roles_privilege_details rpd left join privileges p on rpd.module_id = p.id where p.parent_id = 0 AND role_id = " + roleId + " ORDER BY p.sort_id");
        return this.getHibernateTemplate().executeFind(callBack);
    }

    @Override
    public List<Integer> getRoleModuleMenus(Long roleId, Long moduleId) {
        //RolesPrivilegeDetail.find(:all,:include=>[:module],:select=>"DISTINCT rpd.module_id",:conditions=>["p.parent_id = 0"],:joins=>" as rpd left join privileges as p on rpd.role_id = #{roleId} AND rpd.company_id = #{companyId} AND rpd.is_locked = 0 AND rpd.module_id = p.id",:order=>"p.sort_id")
        //List<RolesPrivilegeDetail> rolesPrivilegeDetails = this.getHibernateTemplate().find("select rpd.module_id from RolesPrivilegeDetail rpd left join Privilege p on rpd.moduleId = p.id");
        //List<RolesPrivilegeDetail> rolesPrivilegeDetails = this.getHibernateTemplate().getSessionFactory().openSession().createQuery("select rpd.module_id from RolesPrivilegeDetail rpd left join Privilege p on rpd.moduleId = p.id");
        HQLCallBackUtil callBack = new HQLCallBackUtil();
        String sql = "select DISTINCT rpd.menu_id "
                + "from roles_privilege_details rpd "
                + "left join privileges p on rpd.module_id = p.id "
                + "where rpd.module_id = " + moduleId + " AND rpd.is_locked = 0 AND rpd.role_id = " + roleId
                + " ORDER BY p.sort_id";
        callBack.setSql(sql);
        return this.getHibernateTemplate().executeFind(callBack);
    }

    @Override
    public String roleLock(Long roleId) {
        Role role = getRoleById(roleId);
        if (role.getIsLocked() == 1) {
            role.setIsLocked((long) 0);
        } else {
            role.setIsLocked((long) 1);
        }
        this.saveOrUpdate(role);
        String info = "success";
        String jsonStr = "{success:true,info:\"" + info + "\"}";
        return jsonStr;
    }
}
