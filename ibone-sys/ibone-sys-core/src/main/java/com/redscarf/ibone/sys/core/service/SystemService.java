package com.redscarf.ibone.sys.core.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redscarf.ibone.common.model.common.PageRequest;
import com.redscarf.ibone.common.model.system.CreateSystemModel;
import com.redscarf.ibone.common.model.system.UpdateSystemModel;
import com.redscarf.ibone.sys.core.mapper.RbacSystemMapper;
import com.redscarf.ibone.sys.core.model.po.RbacSystemEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemService {

    @Autowired
    private RbacSystemMapper rbacSystemMapper;

    public void save(CreateSystemModel systemModel){
        RbacSystemEntity systemEntity = new RbacSystemEntity();
        BeanUtils.copyProperties(systemModel,systemEntity);
        rbacSystemMapper.insert(systemEntity);
    }

    public void update(UpdateSystemModel systemModel){
        RbacSystemEntity systemEntity = rbacSystemMapper.selectByPrimaryKey(systemModel.getId());
        BeanUtils.copyProperties(systemModel,systemEntity);
        rbacSystemMapper.insert(systemEntity);
    }

    public void delete(String ids){
        String[] idArray =  ids.split(",");
        for (String id:idArray){
            if(StringUtils.isBlank(id)){
                continue;
            }
            rbacSystemMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
    }

    public RbacSystemEntity get(int id){
        return rbacSystemMapper.selectByPrimaryKey(id);
    }

    public List<RbacSystemEntity> findAll(){
        return rbacSystemMapper.selectAll();
    }

    /**
     * 这里的service即casFilter
     * @param service
     * @return
     */
    public String findServiceTheme(String service){
        List<RbacSystemEntity> systemEntities = rbacSystemMapper.findByServiceCasFilterOrderByServiceEvaluationOrderDesc(service);

        if(systemEntities != null && !systemEntities.isEmpty()){
            RbacSystemEntity entity = systemEntities.get(0);
            return entity.getServiceThemePath();
        }

        return "cas-theme-default";
    }


    public PageInfo<RbacSystemEntity> findPage(String searchKey, PageRequest pageRequest){
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize());
        List<RbacSystemEntity> systemEntityList = rbacSystemMapper.findPageByNameAndPermissionValue(searchKey);
        return new PageInfo<>(systemEntityList);
    }



}
