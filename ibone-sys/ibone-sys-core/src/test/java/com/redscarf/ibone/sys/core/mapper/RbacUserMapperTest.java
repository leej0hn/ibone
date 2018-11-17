
package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.BaseDaoTest;
import com.redscarf.ibone.sys.core.model.po.RbacUserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
public class RbacUserMapperTest extends BaseDaoTest {
    @Autowired
    RbacUserMapper mapper;

    @Test
    public void findPageByUserNameAndRealNameAndPhoneAndEmail(){
        List<RbacUserEntity> roleList = mapper.findPageByUserNameAndRealNameAndPhoneAndEmail("");
        System.out.println(roleList);
        List<RbacUserEntity> roleList2 = mapper.findPageByUserNameAndRealNameAndPhoneAndEmail("test123");
        System.out.println(roleList2);
    }

    @Test
    public void  insertUserMenu(){
        mapper.insertUserMenu(1,1);
    }

    @Test
    public void deleteUserMenu(){
        mapper.deleteUserMenu(1,1);
    }

    @Test
    public void  insertUserRole(){
        mapper.insertUserRole(1,1);
    }


    @Test
    public void  insertUserPermission(){
        mapper.insertUserPermission(1,2);
    }

    @Test
    public void deleteUserPermission(){
        mapper.deleteUserPermission(1,2);
    }


    @Test
    public void insertUserOrganization(){
        mapper.insertUserOrganization(1,2);
    }

    @Test
    public void deleteUserOrganization(){
        mapper.deleteUserOrganization(1,2);
    }
}
