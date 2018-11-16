package com.redscarf.ibone.sys.core.service;

import com.redscarf.ibone.common.model.menu.CreateMenuModel;
import com.redscarf.ibone.common.model.menu.TreeMenuModel;
import com.redscarf.ibone.common.model.menu.UpdateMenuModel;
import com.redscarf.ibone.sys.core.mapper.RbacMenuMapper;
import com.redscarf.ibone.sys.core.model.po.RbacMenuEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private RbacMenuMapper rbacMenuMapper;

    //获取树形菜单
    public List<TreeMenuModel> findBySystemId(int systemId){
        List<TreeMenuModel> menuModelList = getTreeMenu(systemId,null);
        return menuModelList;
    }

    private List<TreeMenuModel> getTreeMenu(int systemId,TreeMenuModel menuModel){
        int pid = 0;
        if(menuModel != null){
            pid = menuModel.getId();
        }
        List<RbacMenuEntity> menuEntityList = rbacMenuMapper.findByPidAndSystemIdOrderByOrdersDesc(pid,systemId);
        List<TreeMenuModel> menuModelList = new ArrayList<>();
        if(menuEntityList != null && !menuEntityList.isEmpty()){
            for (RbacMenuEntity menuEntity : menuEntityList){
                TreeMenuModel treeMenuModel = new TreeMenuModel();
                BeanUtils.copyProperties(menuEntity,treeMenuModel);
                menuModelList.add(treeMenuModel);
                getTreeMenu(systemId,treeMenuModel);
            }

            if (menuModel != null) {
                menuModel.setChildren(menuModelList);
            }

        }
        return menuModelList;
    }


    public TreeMenuModel get(int id){
        RbacMenuEntity menuEntity = rbacMenuMapper.selectByPrimaryKey(id);
        TreeMenuModel menuModel = new TreeMenuModel();
        BeanUtils.copyProperties(menuEntity,menuModel);
        return menuModel;
    }

    public void add(CreateMenuModel menuModel){
        RbacMenuEntity menuEntity = new RbacMenuEntity();
        BeanUtils.copyProperties(menuModel,menuEntity);
        rbacMenuMapper.insert(menuEntity);
    }

    public void delete(int id){
        RbacMenuEntity menuEntity = rbacMenuMapper.selectByPrimaryKey(id);
        rbacMenuMapper.delete(menuEntity);
    }

    public void update(UpdateMenuModel menuModel){
        RbacMenuEntity menuEntity = rbacMenuMapper.selectByPrimaryKey(menuModel.getId());
        BeanUtils.copyProperties(menuModel,menuEntity);
        rbacMenuMapper.insert(menuEntity);
    }
}
