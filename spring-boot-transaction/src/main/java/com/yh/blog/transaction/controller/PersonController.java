package com.yh.blog.transaction.controller;

import com.yh.blog.transaction.dao.BlogDao;
import com.yh.blog.transaction.dao.PersonDao;
import com.yh.blog.transaction.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

/**
 * @author yh128
 * @version 1.0.0
 * @ClassName PersonController.java
 * @Description 用户控制类
 * @Param
 * @createTime 2020年01月02日 16:33:00
 */
@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @GetMapping(value = "addByDataTran")
    public void addByDataTran() {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        // 可以使用默认的，也可以自己new了配置
//        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        Person person = new Person("编程式", 21, "上海");
        try {
            personDao.save(person);
            // 弄一个异常看看是否会回滚
            int i = 1 / 0;
            // 需要手动提交事务（不会自动提交的哦）
            log.info("Person数据新增成功id={}", person.getId());
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            log.error("使用编程式事务回滚:id={}", person.getId());
            // 需要手动回滚
            dataSourceTransactionManager.rollback(transactionStatus);
        }
    }

    /**
     * 在transactionTemplate只要发生异常都会自动回滚
     */
    @GetMapping(value = "addByTemplate")
    public void addByTemplate() {
        transactionTemplate.execute((status -> {
            Person person = new Person("使用Template", 29, "广东广州");
            // 弄一个异常看看是否会回滚
            personDao.save(person);
            int i = 1 / 0;
            log.info("Person数据新增成功id={}", person.getId());
            return status;
        }));
    }

    /**
     * savePerson方法前缀式save开头所以会进入全局事务
     */
    @GetMapping(value = "savePerson")
    public void savePerson() {
        Person person = new Person("savePerson", 23, "广东广州");
        // 弄一个异常看看是否会回滚
        personDao.save(person);
        int i = 1 / 0;
        log.info("Person数据新增成功id={}", person.getId());
    }

    /**
     * addPerson方法没有配置全局事务也没有开启类,方法的事务
     */
    @GetMapping(value = "addPerson")
    public void addPerson() {
        Person person = new Person("addPerson", 23, "广东广州");
        // 弄一个异常看看是否会回滚0
        personDao.save(person);
        log.info("Person数据新增成功id={}", person.getId());
        int i = 1 / 0;
    }


}
