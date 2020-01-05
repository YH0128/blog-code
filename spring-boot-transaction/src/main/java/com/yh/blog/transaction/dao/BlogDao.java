package com.yh.blog.transaction.dao;

import com.yh.blog.transaction.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yh128
 * @version 1.0.0
 * @ClassName BlogDao.java
 * @Description 博客dao层
 * @Param
 * @createTime 2020年01月02日 16:25:00
 */
@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {

}
