
package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.BaseDaoTest;
import com.redscarf.ibone.sys.core.model.po.RbacPermissionEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
public class RbacPermissionMapperTest extends BaseDaoTest {
    @Autowired
    RbacPermissionMapper mapper;

    @Test
    public void testFindPageByNameAndPermissionValue() throws Exception{
        List<RbacPermissionEntity> menuList = mapper.findPageByNameAndPermissionValue("查看");
        System.out.println(menuList);
    }


    @Test
    public void testFindPermissionsByRoleId() throws Exception{
        List<RbacPermissionEntity> permissionsByRoleId = mapper.findPermissionsByRoleId(10);
        System.out.println(permissionsByRoleId);
    }

    @Test
    public void testFindByIdIn(){
        int[] ids = new int[]{93,94,95};
        List<RbacPermissionEntity> byIdIn = mapper.findByIdIn(ids);
        System.out.println(byIdIn);
    }

}
