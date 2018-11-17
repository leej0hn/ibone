package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.RbacUserEntity;
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
            "<if test = \"username != null and username != '' \" > " ,
            "AND username LIKE CONCAT('%', #{username}, '%') ",
            "</if> " ,
            "<if test = \"realname != null and realname != '' \"> " ,
            "AND realname LIKE CONCAT('%', #{realname}, '%') " ,
            "</if> " ,
            "<if test = \"phone != null and phone != '' \"> " ,
            "AND phone LIKE CONCAT('%', #{phone}, '%') " ,
            "</if> " ,
            "<if test = \"email != null and email != '' \"> " ,
            "AND email LIKE CONCAT('%', #{email}, '%') " ,
            "</if> " ,
            "</where> " ,
            "</script>"
    })
    List<RbacUserEntity> findPageByUserNameAndRealNameAndPhoneAndEmail(@Param("username") String username,
                                                                       @Param("realname") String realname,
                                                                       @Param("phone")String phone,
                                                                       @Param("email")String email);

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE a.username = #{username} ",
            "</script>"
    })
    RbacUserEntity findByUsername(@Param("username")String username);

}
