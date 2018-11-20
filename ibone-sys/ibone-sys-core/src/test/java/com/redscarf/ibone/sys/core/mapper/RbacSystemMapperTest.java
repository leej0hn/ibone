
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
        List<RbacSystemEntity> list = mapper.findByServiceCasFilterOrderByServiceEvaluationOrderDesc("http://sm-admin.ibone.com:10002/cas111");
        System.out.println(list);
    }

    @Test
    public void testFindPageByNameAndPermissionValue() throws Exception{
        List<RbacSystemEntity> roleList = mapper.findPageByNameAndPermissionValue("");
        System.out.println(roleList);
        List<RbacSystemEntity> roleList2 = mapper.findPageByNameAndPermissionValue("测试系统");
        System.out.println(roleList2);
    }


}
