package io.seata.samples.integration.call.tccaction;

import com.alibaba.dubbo.config.annotation.Reference;
import io.seata.core.context.RootContext;
import io.seata.samples.integration.call.tccaction.tcc.TccActionOne;
import io.seata.samples.integration.call.tccaction.tcc.TccActionTwo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Tcc transaction service.
 *
 * @author zhangsen
 */
@Service
public class TccTransactionService {

    @Reference(version = "1.0.0")
    private TccActionOne tccActionOne;

    @Reference(version = "1.0.0")
    private TccActionTwo tccActionTwo;

    /**
     * 发起分布式事务
     *
     * @return string string
     */
    @GlobalTransactional
    public String doTransactionCommit() {
        //第一个TCC 事务参与者
        boolean result = tccActionOne.prepare(null, 1);
        if (!result) {
            throw new RuntimeException("TccActionOne failed.");
        }
        List list = new ArrayList();
        list.add("c1");
        list.add("c2");
        result = tccActionTwo.prepare(null, "two", list);
        if (!result) {
            throw new RuntimeException("TccActionTwo failed.");
        }
        return RootContext.getXID();
    }

    /**
     * Do transaction rollback string.
     *
     * @param map the map
     * @return the string
     */
    @GlobalTransactional
    public String doTransactionRollback(Map map) {
        //第一个TCC 事务参与者
        boolean result = tccActionOne.prepare(null, 1);
        if (!result) {
            throw new RuntimeException("TccActionOne failed.");
        }
        List list = new ArrayList();
        list.add("c1");
        list.add("c2");
        result = tccActionTwo.prepare(null, "two", list);
        if (!result) {
            throw new RuntimeException("TccActionTwo failed.");
        }
        map.put("xid", RootContext.getXID());
        throw new RuntimeException("transacton rollback");
    }
}
