package com.roots.cms.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author admin
 * @ClassName Pagination.java
 * @Description 分页对象
 * @createTime 2020年08月05日 20:21:00
 */
@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Pagination<T> extends Page<T> {

    private static final long serialVersionUID = 1L;

    public Pagination(long current, long size) {
        super(current, size);
    }

    //总页数
    private long pages;

    //前一页
    private long prePage;
    //下一页
    private long nextPage;

    //是否为第一页
    private boolean isFirstPage;
    //是否为最后一页
    private boolean isLastPage;
    //是否有前一页
    private boolean hasPreviousPage;
    //是否有下一页
    private boolean hasNextPage;

    @Override
    public Pagination<T> setTotal(long total) {
        super.setTotal(total);
        pages = (total + getSize() - 1) / getSize();
        long current = getCurrent();
        isFirstPage = current == 1;
        isLastPage = current == pages || pages == 0;
        hasPreviousPage = current > 1;
        hasNextPage = current < pages;
        if (current > 1) {
            prePage = current - 1;
        }
        if (current < pages) {
            nextPage = current + 1;
        }
        return this;
    }
}
