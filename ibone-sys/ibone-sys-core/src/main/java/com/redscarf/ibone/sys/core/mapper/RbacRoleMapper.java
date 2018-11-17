package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacRoleEntity;
import org.apache.ibatis.annotations.*;

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
                "<if test = \"searchKey != null and searchKey != '' \" > " ,
                "OR name LIKE CONCAT('%', #{searchKey}, '%') ",
                "OR title LIKE CONCAT('%', #{searchKey}, '%') " ,
                "</if> " ,
            "</where> " ,
            "</script>"
    })
    List<RbacRoleEntity> findPageByNameAndTitle(@Param("searchKey") String searchKey);


    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "INNER JOIN  rbac_user_role AS ur ON ur.role_id = a.id " ,
            "WHERE ur.user_id = #{userId} ",
            "</script>"
    })
    List<RbacRoleEntity> findRolesByUserId(@Param("userId") int userId);


    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE 1=1 " ,
            "AND  a.id IN ",
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'> #{id} </foreach> " ,
            "</script>"
    })
    List<RbacRoleEntity> findByIdIn(@Param("ids")int[] ids);


    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE a.name = #{name} ",
            "</script>"
    })
    List<RbacRoleEntity> findByName(@Param("name")String name);

    /**
     * 删除角色菜单
     * @param roleId
     * @param menuId
     */
    @Delete({
            "<script>",
            "DELETE " ,
            "FROM rbac_role_menu" ,
            "WHERE role_id = #{roleId} " ,
            "AND menu_id = #{menuId}",
            "</script>"
    })
    void deleteRoleMenu(@Param("roleId")int roleId,@Param("menuId")int menuId);

    /**
     * 关联角色菜单
     * @param roleId
     * @param menuId
     */
    @Insert({
            "<script>",
            "INSERT  " ,
            "INTO rbac_role_menu" ,
            "(role_id , menu_id)  " ,
            "values (#{roleId},#{menuId}) ",
            "</script>"
    })
    void insertRoleMenu(@Param("roleId")int roleId,@Param("menuId")int menuId);


    /**
     * 关联角色权限关系
     * @param roleId
     * @param permissionId
     */
    @Insert({
            "<script>",
            "INSERT  " ,
            "INTO rbac_role_permission  " ,
            "(role_id , permission_id)  " ,
            "values (#{roleId},#{permissionId}) ",
            "</script>"
    })
    void insertRolePermission(@Param("roleId")int roleId,@Param("permissionId")int permissionId);

    /**
     * 删除角色权限关系
     * @param roleId
     * @param permissionId
     */
    @Delete({
            "<script>",
            "DELETE  " ,
            "FROM rbac_role_permission  " ,
            "WHERE role_id = #{roleId} " ,
            "AND permission_id = #{permissionId}",
            "</script>"
    })
    void deleteRolePermission(@Param("roleId")int roleId,@Param("permissionId")int permissionId);


}
