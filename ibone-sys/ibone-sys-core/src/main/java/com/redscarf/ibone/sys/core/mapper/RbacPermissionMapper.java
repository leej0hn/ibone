package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacPermissionEntity;
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
public interface RbacPermissionMapper extends IBaseMapper<RbacPermissionEntity>{
    String ALL_COLUMN =
            " a.id AS id , " +
            " a.system_id AS systemId , " +
            " a.menu_id AS menuId , " +
            " a.name AS name , " +
            " a.type AS type , " +
            " a.permission_value AS permissionValue , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime  " ;


    String TABLE_NAME = " rbac_permission ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "<where> " ,
                "<if test = \"searchKey != null and searchKey != '' \" > " ,
                "OR name LIKE CONCAT('%', #{searchKey}, '%') ",
                "OR permission_value LIKE CONCAT('%', #{searchKey}, '%') " ,
                "</if> " ,
            "</where> " ,
            "</script>"
    })
    List<RbacPermissionEntity> findPageByNameAndPermissionValue(@Param("searchKey") String searchKey);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "INNER JOIN  rbac_role_permission AS rp ON rp.permission_id = a.id " ,
            "WHERE rp.role_id = #{roleId} ",
            "</script>"
    })
    List<RbacPermissionEntity> findPermissionsByRoleId(@Param("roleId")int roleId);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "INNER JOIN  rbac_user_permission AS up ON ur.permission_id = a.id " ,
            "WHERE up.user_id = #{userId} ",
            "</script>"
    })
    List<RbacPermissionEntity> findPermissionsByUserId(@Param("userId") int userId);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE 1=1 " ,
            "AND  a.id IN ",
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'> #{id} </foreach> " ,
            "</script>"
    })
    List<RbacPermissionEntity> findByIdIn(@Param("ids")int[] ids);
}
