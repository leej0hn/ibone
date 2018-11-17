package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacUserSecurityQuestionsEntity;
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
public interface RbacUserSecurityQuestionsMapper extends IBaseMapper<RbacUserSecurityQuestionsEntity>{

    String ALL_COLUMN =
            " a.id AS id , " +
            " a.user_id AS userId , " +
            " a.question AS question , " +
            " a.answer AS answer , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime  " ;


    String TABLE_NAME = " rbac_user_securityquestions ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE a.user_id = #{userId} ",
            "</script>"
    })
    List<RbacUserSecurityQuestionsEntity> findByUserId(@Param("userId")int userId);

}
