package com.github.paging.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页对象
 * @author 小帅
 * @version 1.0
 * @date 2024/3/24 13:16
 */

@Data
@Accessors(chain = true)
public class Page implements Serializable {


    @Serial
    private final static long serialVersionUID = 1L;

    // 当前查询第几页
    private Integer pageNum;

    // 每页显示多少条数据
    private Integer pageSize;
}
