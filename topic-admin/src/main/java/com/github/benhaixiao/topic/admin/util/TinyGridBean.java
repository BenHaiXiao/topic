package com.github.benhaixiao.topic.admin.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * 响应tinygrid控件的信息.
 *
 * @author xiaobenhai
 */
public class TinyGridBean {

    private int page;
    private int total;
    private String extra;
    private List<Map<String, String>> rows = Lists.newArrayList();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }

    public void addRow(Map<String, String> row) {
        rows.add(row);
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
