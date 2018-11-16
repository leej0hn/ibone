
package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.BaseDaoTest;
import com.redscarf.ibone.sys.core.model.po.RbacMenuEntity;
import com.redscarf.ibone.sys.core.model.po.SysLogEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
public class RbacMenuMapperTest extends BaseDaoTest {
    @Autowired
    RbacMenuMapper mapper;

    @Test
    public void testFindByPidAndSystemIdOrderByOrdersDesc() throws Exception{
        List<RbacMenuEntity> menuList = mapper.findByPidAndSystemIdOrderByOrdersDesc(0, 3);
        System.out.println(menuList);
    }


    @Test
    public void testFindMenusByRoleId(){
        List<RbacMenuEntity> menusByRoleId = mapper.findMenusByRoleId(12);
        System.out.println(menusByRoleId);
    }

    @Test
    public void testFindByIdIn(){
        int[] ids = new int[]{9,10,13};
        List<RbacMenuEntity> byIdIn = mapper.findByIdIn(ids);
    }
}
