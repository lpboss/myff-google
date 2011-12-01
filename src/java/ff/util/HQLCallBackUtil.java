/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ff.util;

import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author jerry
 */
public class HQLCallBackUtil implements HibernateCallback {

    private String hql;
    private String sql;

    public HQLCallBackUtil() {
    }

    public HQLCallBackUtil(String hql) {
        this.hql = hql;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object doInHibernate(Session s) throws HibernateException,
            SQLException {
        //优先运行SQL，createSQLQuery
        if (sql == null || sql.equals("")) {
            if (hql == null || hql.equals("")) {
                throw new HibernateException("Can't execute NULL hql!");
            }
            return s.createQuery(hql).list();
        } else {
            return s.createSQLQuery(sql).list();
        }

    }
}
