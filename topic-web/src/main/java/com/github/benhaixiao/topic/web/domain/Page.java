package com.github.benhaixiao.topic.web.domain;

/**
 * @author xiaobenhai
 *
 */
public class Page {
    private long total = 0;
    private Object last = null;
    private int count = 0;
    private boolean more = false;

    public Page setTotal(long total){
        this.total = total;
        return this;
    }

    public Page setLast(Object last){
        this.last = last;
        return this;
    }

    public Page setCount(int count){
        this.count = count;
        return this;
    }

    public void setMore(boolean more){
        this.more = more;
    }

    public long getTotal() {
        return total;
    }

    public Object getLast() {
        return last;
    }

    public int getCount() {
        return count;
    }

    public boolean isMore() {
        return more;
    }
}
