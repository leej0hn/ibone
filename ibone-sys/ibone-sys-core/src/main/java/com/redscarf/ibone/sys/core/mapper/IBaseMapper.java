package com.redscarf.ibone.sys.core.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * @author LeeJohn
 * @date 2017/12/25
 */
public interface IBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
