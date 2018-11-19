package com.redscarf.ibone.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * Session生成器
 */
public class IboneSessionFactory implements SessionFactory {
    @Override
    public Session createSession(SessionContext sessionContext) {
        IboneSession session = new IboneSession();
        return session;
    }
}
