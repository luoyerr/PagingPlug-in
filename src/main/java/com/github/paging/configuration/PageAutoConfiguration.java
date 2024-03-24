package com.github.paging.configuration;

import com.github.paging.inspect.PageAspect;
import com.github.paging.plugin.PagePlugin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 小帅
 * @version 1.0
 * @date 2024/3/24 13:51
 */
@Configuration
public class PageAutoConfiguration {

    /**
     * @Bean 注解
     * 将标记返回值 注入到spring容器中
     *
     * Conditional - 条件
     * @return
     */

    @Bean
    @ConditionalOnProperty(prefix = "paging.page",name = "enabled",havingValue = "true",matchIfMissing = true)
    public PagePlugin getPagePlugin(){
        return new PagePlugin();
    }


    @Bean
    @ConditionalOnProperty(prefix = "paging.page",name = "enabled",havingValue = "true",matchIfMissing = true)
    public PageAspect getPageAspect() {
        return new PageAspect();
    }

}