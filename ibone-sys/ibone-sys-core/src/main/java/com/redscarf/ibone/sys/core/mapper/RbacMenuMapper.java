package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacMenuEntity;
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
public interface RbacMenuMapper extends IBaseMapper<RbacMenuEntity>{
    String ALL_COLUMN =
            " a.id AS id , " +
            " a.system_id AS systemId , " +
            " a.pid AS pid , " +
            " a.name AS name , " +
            " a.url AS url , " +
            " a.target AS target , " +
            " a.orders AS orders , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime  " ;


    String TABLE_NAME = " rbac_menu ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";

    @Select({
        "SELECT " + ALL_COLUMN ,
        "FROM  " + TABLE_NAME_AS ,
        "WHERE 1=1 " ,
        "AND  a.pid = #{pid} " ,
        "AND a.system_id = #{systemId} " ,
        "ORDER BY a.orders DESC "
    })
    List<RbacMenuEntity> findByPidAndSystemIdOrderByOrdersDesc(@Param("pid") int pid,
                                                               @Param("systemId") int systemId);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "INNER JOIN  rbac_role_menu AS rm ON rm.menu_id = a.id " ,
            "WHERE rm.role_id = #{roleId} ",
            "</script>"
    })
    List<RbacMenuEntity> findMenusByRoleId(@Param("roleId") int roleId);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "INNER JOIN  rbac_user_menu AS um ON um.menu_id = a.id " ,
            "WHERE um.user_id = #{userId} ",
            "</script>"
    })
    List<RbacMenuEntity> findMenusByUserId(@Param("userId") int userId);


    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE 1=1 " ,
            "AND  a.id IN ",
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'> #{id} </foreach> " ,
            "</script>"
    })
    List<RbacMenuEntity> findByIdIn(@Param("ids")int[] ids);


    @Select({
            "<script>",
            "SELECT  DISTINCT " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "LEFT OUTER JOIN rbac_role_menu rm on rm.menu_id = a.id " ,
            "LEFT OUTER JOIN rbac_role role on rm.role_id = role.id" ,
            "WHERE 1=1 " ,
            "AND  role.id IN ",
            "<foreach item='roleId' index='index' collection='roleIds' open='(' separator=',' close=')'> #{roleId} </foreach> " ,
            "AND a.pid = #{pid}",
            "AND a.system_id = #{systemId}",
            "ORDER BY a.orders DESC",
            "</script>"
    })
    List<RbacMenuEntity> findDistinctByRolesInAndPidAndSystemIdOrderByOrdersDesc(@Param("roleIds") int[] roleIds ,
                                                                                 @Param("pid") int pid ,
                                                                                 @Param("systemId") int systemId);


    @Select({
            "<script>",
            "SELECT  DISTINCT " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "LEFT OUTER JOIN rbac_user_menu um on um.menu_id = a.id ",
            "LEFT OUTER JOIN rbac_user user on user.id = um.user_id ",
            "WHERE 1=1 " ,
            "AND  user.id IN ",
            "<foreach item='userId' index='index' collection='userIds' open='(' separator=',' close=')'> #{userId} </foreach> " ,
            "AND a.pid = #{pid}",
            "AND a.system_id = #{systemId}",
            "ORDER BY a.orders DESC",
            "</script>"
    })
    List<RbacMenuEntity> findDistinctByUsersInAndPidAndSystemIdOrderByOrdersDesc(@Param("userIds") int[] userIds ,
                                                                                 @Param("pid") int pid ,
                                                                                 @Param("systemId") int systemId);
}
