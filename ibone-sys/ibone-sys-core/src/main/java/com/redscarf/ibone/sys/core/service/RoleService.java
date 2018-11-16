package com.redscarf.ibone.sys.core.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redscarf.ibone.common.model.common.AssignPermissionModel;
import com.redscarf.ibone.common.model.common.PageRequest;
import com.redscarf.ibone.common.model.role.AssignMenuModel;
import com.redscarf.ibone.common.model.role.CreateRoleModel;
import com.redscarf.ibone.common.model.role.SimpleRoleModel;
import com.redscarf.ibone.common.model.role.UpdateRoleModel;
import com.redscarf.ibone.sys.core.mapper.RbacMenuMapper;
import com.redscarf.ibone.sys.core.mapper.RbacPermissionMapper;
import com.redscarf.ibone.sys.core.mapper.RbacRoleMapper;
import com.redscarf.ibone.sys.core.model.po.RbacMenuEntity;
import com.redscarf.ibone.sys.core.model.po.RbacPermissionEntity;
import com.redscarf.ibone.sys.core.model.po.RbacRoleEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class RoleService {

    @Autowired
    private RbacRoleMapper roleMapper;
    @Autowired
    private RbacMenuMapper menuMapper;
    @Autowired
    private RbacPermissionMapper permissionMapper;

    public void save(CreateRoleModel roleModel){
        RbacRoleEntity roleEntity = new RbacRoleEntity();
        BeanUtils.copyProperties(roleModel,roleEntity);
        roleMapper.insert(roleEntity);
    }

    public void update(UpdateRoleModel roleModel){
        RbacRoleEntity roleEntity = roleMapper.selectByPrimaryKey(roleModel.getId());
        BeanUtils.copyProperties(roleModel,roleEntity);
        roleMapper.insert(roleEntity);
    }

    public void delete(String ids){
        String[] idArray =  ids.split(",");
        for (String id:idArray){
            if(StringUtils.isBlank(id)){
                continue;
            }
            roleMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
    }

    public RbacRoleEntity get(int id){
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<RbacRoleEntity> findAll(){
        return roleMapper.selectAll();
    }

    public PageInfo<RbacRoleEntity> findPage(String name, String title, PageRequest pageRequest){
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize());
        //分页查找
        List<RbacRoleEntity> permissionList = roleMapper.findPageByNameAndTitle(name, title);
        return new PageInfo<>(permissionList);
    }

    /**
     * 分配菜单
     * @param menuModel
     */
    public void assignMenu(AssignMenuModel menuModel){
        //首先删除该系统下所有菜单
        RbacRoleEntity roleEntity = roleMapper.selectByPrimaryKey(menuModel.getRoleId());
        List<RbacMenuEntity> menuEntities = menuMapper.findMenusByRoleId(roleEntity.getId());
        if(menuEntities != null && !menuEntities.isEmpty()){
            for (int i = 0;i < menuEntities.size(); i++){
                RbacMenuEntity menuEntity = menuEntities.get(i);
                if(menuEntity.getSystemId() == menuModel.getSystemId()){
                    menuEntities.remove(menuEntity);
                    menuMapper.delete(menuEntity);
                    i--;
                }
            }
        }

        //然后插入菜单
        if(menuModel.getRoleMenu() != null && menuModel.getRoleMenu().length > 0){
            List<RbacMenuEntity> newMenus = menuMapper.findByIdIn(menuModel.getRoleMenu());
            menuEntities.addAll(newMenus);
            menuMapper.insertList(newMenus);
        }
    }

    /**
     * 分配权限
     * @param permissionModel
     */
    public void assignPermission(AssignPermissionModel permissionModel){
        //首先删除该系统下所有菜单
        RbacRoleEntity roleEntity = roleMapper.selectByPrimaryKey(permissionModel.getId());
        List<RbacPermissionEntity> permissionEntities = permissionMapper.findPermissionsByRoleId(roleEntity.getId());
        if(permissionEntities != null && !permissionEntities.isEmpty()){
            for (int i = 0;i < permissionEntities.size(); i++){
                RbacPermissionEntity permissionEntity = permissionEntities.get(i);
                if(permissionEntity.getSystemId() == permissionModel.getSystemId()){
                    permissionEntities.remove(permissionEntity);
                    permissionMapper.delete(permissionEntity);
                    i--;
                }
            }
        }

        //然后插入权限
        if(permissionModel.getPermission() != null && permissionModel.getPermission().length > 0){
            List<RbacPermissionEntity> newPermissions = permissionMapper.findByIdIn(permissionModel.getPermission());
            permissionEntities.addAll(newPermissions);
            permissionMapper.insertList(permissionEntities);
        }
    }


    public List<SimpleRoleModel> getSimpleModels(List<RbacRoleEntity> roleEntities){
        List<SimpleRoleModel> result = new ArrayList<>();
        if(roleEntities == null || roleEntities.isEmpty()){
            return result;
        }

        for (RbacRoleEntity roleEntity : roleEntities){
            SimpleRoleModel roleModel = new SimpleRoleModel();
            BeanUtils.copyProperties(roleEntity,roleModel);
            result.add(roleModel);
        }
        return result;
    }

    public SimpleRoleModel getSimpleModel(RbacRoleEntity roleEntity){
        SimpleRoleModel roleModel = new SimpleRoleModel();
        BeanUtils.copyProperties(roleEntity,roleModel);
        return roleModel;
    }
}
