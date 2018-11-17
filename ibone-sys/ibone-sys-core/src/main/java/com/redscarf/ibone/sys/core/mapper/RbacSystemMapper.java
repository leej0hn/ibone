package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacSystemEntity;
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
public interface RbacSystemMapper extends IBaseMapper<RbacSystemEntity>{

    String ALL_COLUMN =
            " a.id AS id , " +
            " a.basepath AS basepath , " +
            " a.status AS status , " +
            " a.name AS name , " +
            " a.title AS title , " +
            " a.description AS description , " +
            " a.orders AS orders , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime , " +
            " a.service_registered AS serviceRegistered , " +
            " a.service_cas_filter AS serviceCasFilter , " +
            " a.service_theme_server AS serviceThemeServer , " +
            " a.service_id AS serviceId , " +
            " a.service_description AS serviceDescription , " +
            " a.service_evaluation_order AS serviceEvaluationOrder , " +
            " a.service_name AS serviceName , " +
            " a.service_theme_path AS serviceThemePath  " ;


    String TABLE_NAME = " rbac_system ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";

    @Select({
            "SELECT " + ALL_COLUMN ,
            "FROM  " + TABLE_NAME_AS ,
            "WHERE 1=1 " ,
            "AND  a.service_cas_filter = #{serviceCasFilter} " ,
            "ORDER BY a.service_evaluation_order DESC "
    })
    List<RbacSystemEntity> findByServiceCasFilterOrderByServiceEvaluationOrderDesc(@Param("serviceCasFilter") String serviceCasFilter);


    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "<where> " ,
            "<if test = \"name != null and name != '' \" > " ,
            "AND name LIKE CONCAT('%', #{name}, '%') ",
            "</if> " ,
            "<if test = \"title != null and title != '' \"> " ,
            "AND title LIKE CONCAT('%', #{title}, '%') " ,
            "</if> " ,
            "<if test = \"basepath != null and basepath != '' \"> " ,
            "AND basepath LIKE CONCAT('%', #{basepath}, '%') " ,
            "</if> " ,
            "</where> " ,
            "</script>"
    })
    List<RbacSystemEntity> findPageByNameAndPermissionValue(@Param("name") String name,
                                                            @Param("title") String title,
                                                            @Param("basepath")String basepath);

}
