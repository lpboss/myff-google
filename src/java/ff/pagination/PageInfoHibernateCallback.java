package ff.pagination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class PageInfoHibernateCallback implements HibernateCallback {

	public PageInfoHibernateCallback(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	private PageInfo pageInfo;

	public Object doInHibernate(Session session) throws HibernateException, SQLException {
		List list = new ArrayList();
		Query query = session.createQuery(pageInfo.getHql());
		ScrollableResults scrollableResults = query.scroll();
		scrollableResults.last();
		pageInfo.setTotalCount(scrollableResults.getRowNumber());
		System.out.println("=====================" + (scrollableResults.getRowNumber() + 1));
		query.setFirstResult((pageInfo.getCurPage() - 1) * pageInfo.getPageCount());
		query.setMaxResults(pageInfo.getPageCount());
		list = query.list();

		return list;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}
