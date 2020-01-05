package com.yh.blog.transaction.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局事物配置类
 */
@Aspect
@Configuration
public class GlobalTransactionConfig {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;


    @Bean
    public TransactionInterceptor getTransactionInterceptor() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        Map<String, TransactionAttribute> nameMap = new HashMap<>(16);
        // 只读规则
        RuleBasedTransactionAttribute readOnlyRule = new RuleBasedTransactionAttribute();
        readOnlyRule.setReadOnly(true);
        // transactiondefinition 定义事务的隔离级别；PROPAGATION_REQUIRED 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中
        readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        RuleBasedTransactionAttribute requireRule = new RuleBasedTransactionAttribute();

        // 抛出异常后执行切点回滚
        requireRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        // PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值
        requireRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置事务失效时间，超时则事务回滚
        requireRule.setTimeout(5);

        // 只要controller方法这些字符开头都会自动开启一个事务然后提交或者回滚
        nameMap.put("query*", readOnlyRule);
        nameMap.put("save*", requireRule);
        nameMap.put("update*", requireRule);
        nameMap.put("delete*", requireRule);
        source.setNameMap(nameMap);
        return new TransactionInterceptor(platformTransactionManager, source);
    }


    /**
     * 切入设置
     *
     * @return
     */
    @Bean
    public Advisor getAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 一般都会写的式service层，但是我这里抛弃了service层就写了controller目录
        pointcut.setExpression("execution(* com.yh.blog.transaction.controller.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, getTransactionInterceptor());
    }

}
