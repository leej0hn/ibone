package com.redscarf.ibone.sys.core.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redscarf.ibone.common.exception.IboneException;
import com.redscarf.ibone.common.model.common.PageRequest;
import com.redscarf.ibone.common.model.permission.PermissionBaseInfoModel;
import com.redscarf.ibone.common.model.permission.PermissionCreateModel;
import com.redscarf.ibone.common.model.permission.PermissionUpdateModel;
import com.redscarf.ibone.sys.core.mapper.RbacPermissionMapper;
import com.redscarf.ibone.sys.core.model.po.RbacPermissionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PermissionService {

    @Autowired
    RbacPermissionMapper permissionMapper;

    /**
     * 新增权限
     */
    public void save(PermissionCreateModel createModel){
        RbacPermissionEntity permissionEntity = new RbacPermissionEntity();
        BeanUtils.copyProperties(createModel,permissionEntity);
        permissionMapper.insert(permissionEntity);
    }

    /**
     * 更新权限
     */
    public void update(PermissionUpdateModel updateModel){
        RbacPermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(updateModel.getId());
        if(permissionEntity == null){
            throw new IboneException("没有找到权限");
        }
        BeanUtils.copyProperties(updateModel,permissionEntity);
        permissionMapper.updateByPrimaryKeySelective(permissionEntity);
    }

    /**
     * 删除
     */
    public void delete(String ids){
        String[] idArray =  ids.split(",");
        for (String id:idArray){
            if(StringUtils.isBlank(id)){
                continue;
            }
            permissionMapper.deleteByExample(Integer.parseInt(id));
        }
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    public RbacPermissionEntity findById(int id){
        RbacPermissionEntity permissionEntity = permissionMapper.selectByPrimaryKey(id);
        if(permissionEntity == null){
            throw new IboneException("没有找到权限");
        }
        return permissionEntity;
    }


    /**
     * 分页查询
     * @return
     */
    public PageInfo<RbacPermissionEntity> findPageByNameAndPermissionValue(String searchKey, PageRequest pageRequest){
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize());
        //分页查找
        List<RbacPermissionEntity> permissionEntityList = permissionMapper.findPageByNameAndPermissionValue(searchKey);

        return new PageInfo<>(permissionEntityList);
    }

    /**
     * 获取权限
     * @param systemId
     * @param menuId
     * @return
     */
    public List<PermissionBaseInfoModel> getPermissions(int systemId, int menuId){
        List<RbacPermissionEntity> permissionEntities = null;
        RbacPermissionEntity condition = new RbacPermissionEntity();
        if(menuId != 0){
            condition.setSystemId(systemId);
            condition.setMenuId(menuId);
            permissionEntities = permissionMapper.select(condition);
        }else{
            condition.setMenuId(menuId);
            permissionEntities = permissionMapper.select(condition);
        }
        return getBaseInfos(permissionEntities);
    }

    public List<PermissionBaseInfoModel> getBaseInfos(List<RbacPermissionEntity> permissionEntities){
        List<PermissionBaseInfoModel> permissionBaseInfoModels = new ArrayList<>();
        if(permissionEntities == null || permissionEntities.isEmpty()){
            return permissionBaseInfoModels;
        }
        for (RbacPermissionEntity permissionEntity : permissionEntities){
            PermissionBaseInfoModel baseInfoModel = new PermissionBaseInfoModel();
            BeanUtils.copyProperties(permissionEntity,baseInfoModel);
            permissionBaseInfoModels.add(baseInfoModel);
        }
        return permissionBaseInfoModels;
    }

    public PermissionBaseInfoModel getBaseInfo(RbacPermissionEntity permissionEntity){
        PermissionBaseInfoModel baseInfoModel = new PermissionBaseInfoModel();
        BeanUtils.copyProperties(permissionEntity,baseInfoModel);
        return baseInfoModel;
    }

    public PermissionBaseInfoModel getBaseInfo(int id){
        RbacPermissionEntity permissionEntity = this.findById(id);
        PermissionBaseInfoModel baseInfoModel = new PermissionBaseInfoModel();
        BeanUtils.copyProperties(permissionEntity,baseInfoModel);
        return baseInfoModel;
    }

    public List<RbacPermissionEntity> findPermissionsByRoleId(int roleId){
        return permissionMapper.findPermissionsByRoleId(roleId);
    }

    public List<RbacPermissionEntity> findPermissionsByUserId(int userId){
        return permissionMapper.findPermissionsByUserId(userId);
    }

}
