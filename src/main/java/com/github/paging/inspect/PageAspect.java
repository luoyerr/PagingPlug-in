package com.github.paging.inspect;

import com.github.paging.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author 小帅
 * @version 1.0
 * @date 2024/3/24 13:50
 */
@Aspect
@Component
public class PageAspect {

    /**
     * 增强所有Controller中的所有方法
     *
     * @within(org.springframework.web.bind.annotation.RestController)
     * 所有标记了  @RestController 这个注解类下 ， 所有的方法都会的到增强
     * 作用： 匹配当前连接点所处于的类，如果该类上有@RestController注解，则返回true
     *
     * @return
     */

    @Around("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public Object pageHandler(ProceedingJoinPoint joinPoint) {
        try {
            // -------------前置增强-------------
            // 获取 请求参数 （pageNum pageSize）
            ServletRequestAttributes requestAttributes
                    = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            // 获取页码
            String pageNum = request.getParameter("pageNum");
            // 获取每页条数
            String pageSize = request.getParameter("pageSize");

            System.out.println( "pageNum : => " + pageNum);
            System.out.println("pageSize : =>" + pageSize);


            // 如果没有 直接放行
            if (!StringUtils.hasLength(pageNum) || !StringUtils.hasLength(pageSize)) {
                // 放行
                return joinPoint.proceed();
            }

            // 如果有则封装成 Page对象放入ThreadLocal
            PageUtils.setPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));

            System.out.println("启用 插件");
            //  放行
            Object result = joinPoint.proceed();

            // 后置异常正确
            return result;

        }catch (Throwable e) {
            throw  new RuntimeException(e);
            // 后置异常处理

        }finally {
            // 后置完成增强 - 清空ThreadLocal
            PageUtils.clear();
        }
    }
}
