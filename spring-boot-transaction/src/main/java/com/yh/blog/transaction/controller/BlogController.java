package com.yh.blog.transaction.controller;

import com.yh.blog.transaction.dao.BlogDao;
import com.yh.blog.transaction.entity.Blog;
import com.yh.blog.transaction.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

/**
 * @author yh128
 * @version 1.0.0
 * @ClassName BlogController.java
 * @Description 博文控制类
 * @Param
 * @createTime 2020年01月02日 16:33:00
 */
@RestController
@Slf4j
public class BlogController extends BaseController {

    @Autowired
    private BlogDao blogDao;

    /**
     * 作用在类上，如果抛出异常就回滚
     */
    @GetMapping(value = "addBlogFailToClass")
    public void addBlogFailToClass() {
        Blog blog = new Blog("测试文章ToClass", "书法家深刻搭街坊昆仑山大家快乐的数据库", "描述");
        blogDao.save(blog);
        int i = 1 / 0;
        log.info("新增数据成功,id={}", blog.getId());
    }

    /**
     * 作用在方法上方法自定有回滚
     */
    @GetMapping(value = "addBlogFailToMethod")
    @Transactional
    public void addBlogFailToMethod() {
        Blog blog = new Blog("测试文章ToMethod", "书法家深刻搭街坊昆仑山大家快乐的数据库", "描述");
        try {
            blogDao.save(blog);
            int i = 1 / 0;
            log.info("新增数据成功,id={}", blog.getId());
        } catch (Exception e) {
            log.error("Blog新增异常事务回滚:id={}", blog.getId());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * 成功提交事务(不是public修饰的时候都会提交成功)
     */
    @GetMapping(value = "addBlogSuccess")
    @Transactional
    protected void addBlogSuccess() {
        Blog blog = new Blog("测试文章ToSuccess", "书法家深刻搭街坊昆仑山大家快乐的数据库", "描述");
        blogDao.save(blog);
        log.info("新增数据/成功,id={}", blog.getId());
        int i = 1/0;
    }

}
