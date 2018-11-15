package com.redscarf.ibone.common.utils;

import com.redscarf.ibone.common.api.dto.SearchListDTO;
import com.redscarf.ibone.common.service.vo.SearchListVo;
import org.springframework.beans.BeanUtils;

public class VoDtoUtils {
    public static SearchListVo searchListDTOToVo(SearchListDTO dto){
        if(dto == null){
            return null;
        }
        SearchListVo bo = new SearchListVo();
        BeanUtils.copyProperties(dto,bo);
        return bo;
    }

    public static  SearchListDTO searchListVoToDTO(SearchListVo vo){
        if(vo == null){
            return null;
        }
        SearchListDTO dto = new SearchListDTO();
        BeanUtils.copyProperties(vo,dto);
        return dto;
    }
}
