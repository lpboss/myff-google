package ff.pagination;

import java.util.List;

public class PageInfo {

    private List list; // 返回记录集
    private String hql;// 查询语句
    private String action = ""; // 分页时服务器Action的位置
    private int space = 5;//当前页，左，右可以显示出来的页数，space*2+1即为可以显示的页籹。
    private int totalCount = 0; // 总记录数
    private int pageCount = 0; // 每页记录数
    private int pageTotalNum = 0; // 总页数
    private int curPage = 0; // 当前页
    private boolean haveNext = false; // 是否有下一页
    private boolean havePre = false; // 是否有上一页

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    /**
     * pageCount,设置一次取多少条记录。
     */
    public void setTotalCount(int totalCount) {
        // 这里是根据游标的数字传入的，所以要加1
        if (totalCount >= 0) {
            this.totalCount = totalCount + 1;
            // 判断页数
            if ((this.totalCount / this.pageCount) > 0) {
                if ((this.totalCount % this.pageCount) > 0) {
                    this.setPageTotalNum((this.totalCount / this.pageCount) + 1);
                } else {
                    this.setPageTotalNum(this.totalCount / this.pageCount);
                }
            } else if ((this.totalCount / this.pageCount) == 0) {
                if ((this.totalCount % this.pageCount) > 0) {
                    this.setPageTotalNum(1);
                } else {
                    this.setPageTotalNum(0);
                }
            }
        } else {
            this.totalCount = 0;
        }
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageTotalNum() {
        return pageTotalNum;
    }

    public void setPageTotalNum(int pageTotalNum) {
        this.pageTotalNum = pageTotalNum;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public boolean isHaveNext() {
        return haveNext;
    }

    public void setHaveNext(boolean haveNext) {
        this.haveNext = haveNext;
    }

    public boolean isHavePre() {
        return havePre;
    }

    public void setHavePre(boolean havePre) {
        this.havePre = havePre;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }
}
