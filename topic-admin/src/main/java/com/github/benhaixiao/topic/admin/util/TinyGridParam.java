package com.github.benhaixiao.topic.admin.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * jquery控件传递到后台的参数.
 *
 * @author xiaobenhai
 */
public class TinyGridParam {

    private static final Logger LOG = LoggerFactory.getLogger(TinyGridParam.class);

    private static final String FIRST_PAGE = "1";
    private static final String DEFAULT_RP = "10";

    private String query;
    private String queryType;
    private boolean showTotal = false;
    private int page;
    private int resultsPerPage;
    private String sortOrder;
    private String sortName;
    private String columns;

    public TinyGridParam(HttpServletRequest request) {
        this.query = request.getParameter("query");
        this.queryType = request.getParameter("qtype");

        this.sortOrder = request.getParameter("sortorder");
        this.sortName = request.getParameter("sortname");
        this.columns = request.getParameter("columns");

        try {
            this.page = Integer.valueOf(StringUtils.defaultString(request.getParameter("page"), FIRST_PAGE)).intValue();
            if (StringUtils.isNotBlank(request.getParameter("_p"))) {
                this.page = Integer.valueOf(request.getParameter("_p"));
            }
            this.resultsPerPage = Integer.valueOf(StringUtils.defaultString(request.getParameter("rp"), DEFAULT_RP))
                    .intValue();
            this.showTotal = BooleanUtils.toBoolean(StringUtils.defaultString(request.getParameter("showTotal"), "true"));
            if (StringUtils.isNotBlank(request.getParameter("_st"))) {
                this.showTotal = BooleanUtils.toBoolean(request.getParameter("_st"));
            }
        } catch (NumberFormatException e) {
            LOG.error("前台传递给后台的TinyGrid参数出错", e);
        }
    }

    /**
     * 用于分页, 表示跳过的记录数.
     */
    public int getSkip() {
        return ((this.page - 1) * this.resultsPerPage);
    }

    /**
     * 用于分页, 表示需要查询出来的记录数.
     */
    public int getMax() {
        return this.resultsPerPage;
    }

    public String getQuery() {
        return this.query;
    }

    /**
     * JavaBean里的属性信息, 表示前台需显示的字段信息.
     */
    public String[] getBeanProperties() {
        String column = StringUtils.defaultString(this.columns);
        return column.split("\\s*,\\s*");
    }

    public String getQueryType() {
        return this.queryType;
    }

    /**
     * tinygrie当前的显示的页数.
     */
    public int getPage() {
        return this.page;
    }

    /**
     * tinygrid当前页显示的记录数.
     */
    public int getResultsPerPage() {
        return this.resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    /**
     * 用于排序, 升序还是降序?
     */
    public String getSortOrder() {
        return this.sortOrder;
    }

    /**
     * 用于排序, 表示排序的字段.
     */
    public String getSortName() {
        return this.sortName;
    }

    /**
     * SQL里的标准排序语句, 格式如: column_name asc
     */
    public String getOrderSql() {
        if (StringUtils.isEmpty(this.sortName)) {
            return null;
        }
        return this.sortName + " " + this.sortOrder;
    }

    /**
     * 是否求总数?
     */
    public boolean isShowTotal() {
        return showTotal;
    }
}
