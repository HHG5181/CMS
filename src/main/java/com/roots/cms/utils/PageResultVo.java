package com.roots.cms.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author admin
 * @ClassName PageResultVo.java
 * @Description TODO
 * @createTime 2020年08月05日 20:36:00
 */
@Data
@AllArgsConstructor
public class PageResultVo {
    private List rows;
    private Long total;
}
