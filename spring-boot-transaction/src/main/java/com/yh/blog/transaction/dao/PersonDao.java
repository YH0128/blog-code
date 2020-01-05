package com.yh.blog.transaction.dao;

import com.yh.blog.transaction.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author yh128
 * @version 1.0.0
 * @ClassName PersonDao.java
 * @Description 用户dao层
 * @Param
 * @createTime 2020年01月02日 16:10:00
 */
@Repository
public interface PersonDao extends JpaRepository<Person, Long> {


}
