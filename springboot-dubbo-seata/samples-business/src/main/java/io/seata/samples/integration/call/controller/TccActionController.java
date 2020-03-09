package io.seata.samples.integration.call.controller;

import io.seata.samples.integration.call.tccaction.TccTransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author zhaorunze
 * @since 2020/3/9
 */
@RestController
@RequestMapping("/business/tcc")
public class TccActionController {

    @Resource
    TccTransactionService tccTransactionService;

    /**
     * 模拟用户购买商品下单业务逻辑流程
     * @Param:`
     * @Return:
     */
    @RequestMapping("/commit")
    void commit(){
        System.out.println(tccTransactionService.doTransactionCommit());
        return;
    }

    @RequestMapping("/rollback")
    void rollback(){
        System.out.println(tccTransactionService.doTransactionRollback(new HashMap(4)));
        return;
    }
}
