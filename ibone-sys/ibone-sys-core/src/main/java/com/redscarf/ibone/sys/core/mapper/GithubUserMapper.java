package com.redscarf.ibone.sys.core.mapper;

import com.redscarf.ibone.sys.core.model.po.GithubUserEntity;
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
public interface GithubUserMapper extends IBaseMapper<GithubUserEntity>{
    String ALL_COLUMN =
            " a.id AS id , " +
            " a.github_id AS githubId , " +
            " a.user_id AS userId , " +
            " a.login AS login , " +
            " a.node_id AS nodeId , " +
            " a.avatar_url AS avatarUrl , " +
            " a.html_url AS htmlUrl , " +
            " a.name AS name , " +
            " a.company AS company , " +
            " a.email AS email , " +
            " a.blog AS blog , " +
            " a.add_time AS addTime , " +
            " a.update_time AS updateTime  " ;


    String TABLE_NAME = " github_user ";
    String TABLE_NAME_AS = TABLE_NAME + " AS a ";

    @Select({
            "<script>",
            "SELECT   " + ALL_COLUMN,
            "FROM  "  + TABLE_NAME_AS,
            "WHERE a.github_id = #{githubId} ",
            "</script>"
    })
    GithubUserEntity findByGithubId(@Param("githubId")long githubId);


}
