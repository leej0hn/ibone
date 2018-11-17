package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacOrganizationEntity;
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
public interface RbacOrganizationMapper extends IBaseMapper<RbacOrganizationEntity>{
    String ALL_COLUMN =
            " a.id AS id , " +
            " a.pid AS pid , " +
            " a.menu_id AS menuId , " +
            " a.name AS name , " +
            " a.description AS description , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime  " ;


    String TABLE_NAME = " rbac_organization ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "INNER JOIN  rbac_user_organization AS uo ON uo.organization_id = a.id " ,
            "WHERE uo.user_id = #{userId} ",
            "</script>"
    })
    List<RbacOrganizationEntity> findOrganizationByUserId(@Param("userId") int userId);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE 1=1 " ,
            "AND  a.id IN ",
            "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'> #{id} </foreach> " ,
            "</script>"
    })
    List<RbacOrganizationEntity> findByIdIn(@Param("ids")int[] ids);
}
