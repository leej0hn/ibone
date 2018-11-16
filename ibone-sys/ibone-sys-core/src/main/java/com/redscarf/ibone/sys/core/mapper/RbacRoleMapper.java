package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacMenuEntity;
import com.redscarf.ibone.sys.core.model.po.RbacRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Mapper
public interface RbacRoleMapper extends IBaseMapper<RbacRoleEntity>{
    String ALL_COLUMN =
            " a.id AS id , " +
            " a.name AS name , " +
            " a.title AS title , " +
            " a.description AS description , " +
            " a.orders AS orders , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime  " ;


    String TABLE_NAME = " rbac_role ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";


    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "<where> " ,
            "<if test = \"name != null and name != '' \" > " ,
            "AND name LIKE CONCAT('%', #{name}, '%') ",
            "</if> " ,
            "<if test = \"title != null and title != '' \"> " ,
            "AND permission_value LIKE CONCAT('%', #{permissionValue}, '%') " ,
            "</if> " ,
            "</where> " ,
            "</script>"
    })
    List<RbacRoleEntity> findPageByNameAndTitle(@Param("name") String name,
                                                @Param("title") String title);

}
