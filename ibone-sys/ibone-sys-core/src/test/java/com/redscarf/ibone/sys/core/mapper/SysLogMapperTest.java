
package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.BaseDaoTest;
import com.redscarf.ibone.sys.core.model.po.SysLogEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
public class SysLogMapperTest extends BaseDaoTest {
    @Autowired
    SysLogMapper mapper;

    @Test
    public void testInsert() throws Exception{
        SysLogEntity entity = new SysLogEntity();
        entity.setDescription("测试日志");
        entity.setUsername("用户名");
        entity.setStartTime(System.currentTimeMillis());
        entity.setSpendTime(30000);
        entity.setBasePath("basePath");
        entity.setUri("uri");
        entity.setUrl("url");
        entity.setMethod("post");
        entity.setParameter("parameter");
        entity.setUserAgent("userAgent");
        entity.setIp("127.0.0.1");
        entity.setResult("result{}");
        entity.setPermissions("permissions");
        int insert = mapper.insert(entity);
        System.out.println("id : " + entity.getId() + " --- insert : " + insert);
    }



}
