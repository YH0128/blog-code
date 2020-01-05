package com.yh.blog.transaction;

import com.yh.blog.transaction.dao.BlogDao;
import com.yh.blog.transaction.dao.PersonDao;
import com.yh.blog.transaction.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootTransactionApplicationTests {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private BlogDao blogDao;

    @Test
    void contextLoads() {
//        blogDao.updateBlog("修改过后的小灰灰", 1L);
//        for (Blog blog : blogDao.getList(20)) {
//            System.out.println(blog.toString());
//        }
//		personDao.save(new Person(2L, "张三", 23,"广东深圳"));
    }

}
