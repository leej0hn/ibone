
package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.BaseDaoTest;
import com.redscarf.ibone.sys.core.model.po.RbacMenuEntity;
import com.redscarf.ibone.sys.core.model.po.RbacSystemEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
public class RbacSystemMapperTest extends BaseDaoTest {
    @Autowired
    RbacSystemMapper mapper;

    @Test
    public void testFindByServiceCasFilterOrderByServiceEvaluationOrderDesc() throws Exception{
        List<RbacSystemEntity> list = mapper.findByServiceCasFilterOrderByServiceEvaluationOrderDesc("http://jbone-sm-admin.majunwei.com:10002/cas111");
        System.out.println(list);
    }

    @Test
    public void testFindPageByNameAndPermissionValue() throws Exception{
        List<RbacSystemEntity> list = mapper.findPageByNameAndPermissionValue("jbone","服务","http");
        System.out.println(list);
    }

}
