package com.github.paging.utils;

/**
 * 分页工具
 * @author 小帅
 * @version 1.0
 * @date 2024/3/24 13:20
 */
public class PageUtils {

    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL = new ThreadLocal<>();

    public static void setPage(Integer pageNum  , Integer pageSize){
        // TODO 输出传来的消息
        // 设置分页信息
        System.out.println("logs : pageNum: "+pageNum+" pageSize: "+pageSize);
        Page page = new Page()
                .setPageNum(pageNum)
                .setPageSize(pageSize);
        PAGE_THREAD_LOCAL.set(page);
    }

    public static Page getPage(){
        return PAGE_THREAD_LOCAL.get();
    }

    public static void clear() {
        PAGE_THREAD_LOCAL.remove();
    }


}
