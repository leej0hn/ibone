package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacUserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Mapper
public interface RbacUserMapper extends IBaseMapper<RbacUserEntity>{
    String ALL_COLUMN =
            " a.id AS id , " +
            " a.username AS username , " +
            " a.password AS password , " +
            " a.realname AS realname , " +
            " a.avatar AS avatar , " +
            " a.phone AS phone , " +
            " a.email AS email , " +
            " a.nick_name AS nickName , " +
            " a.sex AS sex , " +
            " a.locked AS locked , " +
            " a.salt AS salt , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime  " ;


    String TABLE_NAME = " rbac_user ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "<where> " ,
            "<if test = \"searchKey != null and searchKey != '' \" > " ,
            "OR username LIKE CONCAT('%', #{searchKey}, '%') ",
            "OR realname LIKE CONCAT('%', #{searchKey}, '%') " ,
            "OR phone LIKE CONCAT('%', #{searchKey}, '%') " ,
            "OR email LIKE CONCAT('%', #{searchKey}, '%') " ,
            "</if> " ,
            "</where> " ,
            "ORDER BY a.id DESC" ,
            "</script>"
    })
    List<RbacUserEntity> findPageByUserNameAndRealNameAndPhoneAndEmail(@Param("searchKey") String searchKey);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE a.username = #{username} ",
            "</script>"
    })
    RbacUserEntity findByUsername(@Param("username")String username);


    /**
     * 删除用户菜单关系
     * @param userId
     * @param menuId
     */
    @Delete({
            "<script>",
            "DELETE " ,
            "FROM rbac_user_menu " ,
            "WHERE user_id = #{userId} " ,
            "AND menu_id = #{menuId}",
            "</script>"
    })
    void deleteUserMenu(@Param("userId")int userId,@Param("menuId")int menuId);

    /**
     * 关联用户菜单关系
     * @param userId
     * @param menuId
     */
    @Insert({
            "<script>",
            "INSERT  " ,
            "INTO rbac_user_menu " ,
            "(user_id , menu_id)  " ,
            "values (#{userId},#{menuId}) ",
            "</script>"
    })
    void insertUserMenu(@Param("userId")int userId,@Param("menuId")int menuId);


    /**
     * 关联用户角色关系
     * @param userId
     * @param roleId
     */
    @Insert({
            "<script>",
            "INSERT  " ,
            "INTO rbac_user_role  " ,
            "(user_id , role_id)  " ,
            "values (#{userId},#{roleId}) ",
            "</script>"
    })
    void insertUserRole(@Param("userId")int userId,@Param("roleId")int roleId);


    /**
     * 关联用户权限关系
     * @param userId
     * @param permissionId
     */
    @Insert({
            "<script>",
            "INSERT  " ,
            "INTO rbac_user_permission  " ,
            "(user_id , permission_id)  " ,
            "values (#{userId},#{permissionId}) ",
            "</script>"
    })
    void insertUserPermission(@Param("userId")int userId,@Param("permissionId")int permissionId);

    /**
     * 删除用户权限关系
     * @param userId
     * @param permissionId
     */
    @Delete({
            "<script>",
            "DELETE  " ,
            "FROM rbac_user_permission  " ,
            "WHERE user_id = #{userId} " ,
            "AND permission_id = #{permissionId}",
            "</script>"
    })
    void deleteUserPermission(@Param("userId")int userId,@Param("permissionId")int permissionId);


    /**
     * 关联用户组织关系
     * @param userId
     * @param organizationId
     */
    @Insert({
            "<script>",
            "INSERT  " ,
            "INTO rbac_user_organization  " ,
            "(user_id , organization_id)  " ,
            "values (#{userId},#{organizationId}) ",
            "</script>"
    })
    void insertUserOrganization(@Param("userId")int userId,@Param("organizationId")int organizationId);


    /**
     * 删除用户组织关系
     * @param userId
     * @param organizationId
     */
    @Delete({
            "<script>",
            "DELETE  " ,
            "FROM rbac_user_organization  " ,
            "WHERE user_id = #{userId} " ,
            "AND organization_id = #{organizationId}",
            "</script>"
    })
    void deleteUserOrganization(@Param("userId")int userId,@Param("organizationId")int organizationId);










}
