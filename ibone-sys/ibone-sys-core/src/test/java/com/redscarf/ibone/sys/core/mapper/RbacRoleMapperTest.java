
package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.BaseDaoTest;
import com.redscarf.ibone.sys.core.model.po.RbacRoleEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
public class RbacRoleMapperTest extends BaseDaoTest {
    @Autowired
    RbacRoleMapper mapper;

    @Test
    public void findPageByNameAndTitle(){
        List<RbacRoleEntity> roleList = mapper.findPageByNameAndTitle("SSO");
        System.out.println(roleList);
    }

    @Test
    public void  insertRoleMenu(){
        mapper.insertRoleMenu(1,1);
    }

    @Test
    public void deleteRoleMenu(){
        mapper.deleteRoleMenu(1,1);
    }


    @Test
    public void  insertRolePermission(){
        mapper.insertRolePermission(1,2);
    }

    @Test
    public void deleteRolePermission(){
        mapper.deleteRolePermission(1,2);
    }
}
