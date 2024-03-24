package com.github.paging.plugin;

import com.github.paging.utils.Page;
import com.github.paging.utils.PageUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;


import java.sql.Connection;

/**
 *
 * MyBatis 的分页插件
 * @author 小帅
 * @version 1.0
 * @date 2024/3/24 13:33
 */

@Component
@Intercepts(
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class,Integer.class}
        )
)
public class PagePlugin implements Interceptor {
    /**
     * 拦截方法
     * 分页插件拦截逻辑
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 1、 获取原来的SQL语句
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql().toLowerCase().trim();

        // 2、 判断是不是查询总数的SQL语句
        if (!sql.startsWith("select")) {
            // 不是查询语句直接放行
            return invocation.proceed();
        }

        // 判断当前查询是否需要分页
        Page page = PageUtils.getPage();

        if (page == null) {
            // 无需分页 直接放行
            return invocation.proceed();
        }

        // 处理结尾是 ";" 的情况
        if (sql.endsWith(";")) {
            sql = sql.replace(";" , "");
        }

        // 当前SQL需要处理分页 修改成分页 SQL;
        sql += " limit " + (page.getPageNum() - 1) * page.getPageSize() + "," + page.getPageSize();

        // 修改后的SQL替换原来的SQL
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        metaObject.setValue("sql",sql);

        return invocation.proceed();
    }
}
