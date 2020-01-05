package com.yh.blog.transaction.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * 继承了BaseController有异常事务都会回滚，事务的传播性。
 */
@RestController
// 根据需求自定义配置回滚还是提交。。。
@Transactional(rollbackFor = Exception.class)
public class BaseController {
}
