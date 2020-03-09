package io.seata.samples.integration.call.tccaction.tcc.impl;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.samples.integration.call.tccaction.tcc.ResultHolder;
import io.seata.samples.integration.call.tccaction.tcc.TccActionTwo;

/**
 * The type Tcc action two.
 *
 * @author zhangsen
 */
@Service(version = "1.0.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
        timeout = 3000)
public class TccActionTwoImpl implements TccActionTwo {

    @Override
    public boolean prepare(BusinessActionContext actionContext, String b, List list) {
        String xid = actionContext.getXid();
        System.out.println("TccActionTwo prepare, xid:" + xid + ", b:" + b + ", c:" + list.get(1));
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        System.out.println("TccActionTwo commit, xid:" + xid + ", b:" + actionContext.getActionContext("b") + ", c:" + actionContext.getActionContext("c"));
        ResultHolder.setActionTwoResult(xid, "T");
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        System.out.println("TccActionTwo rollback, xid:" + xid  + ", b:" + actionContext.getActionContext("b") + ", c:" + actionContext.getActionContext("c"));
        ResultHolder.setActionTwoResult(xid, "R");
        return true;
    }

}
