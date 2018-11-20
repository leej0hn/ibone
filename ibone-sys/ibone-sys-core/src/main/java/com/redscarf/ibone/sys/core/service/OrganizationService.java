package com.redscarf.ibone.sys.core.service;

import com.redscarf.ibone.common.model.organization.CreateOrganizationModel;
import com.redscarf.ibone.common.model.organization.OrganizationBaseInfoModel;
import com.redscarf.ibone.common.model.organization.TreeOrganizationModel;
import com.redscarf.ibone.common.model.organization.UpdateOrganizationModel;
import com.redscarf.ibone.sys.core.mapper.RbacOrganizationMapper;
import com.redscarf.ibone.sys.core.model.po.RbacOrganizationEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {
    @Autowired
    private RbacOrganizationMapper rbacOrganizationMapper;

    //获取树形组织结构
    public List<TreeOrganizationModel> findTreeOrganizationModel(){
        List<TreeOrganizationModel> organizationModels = getTreeOrganization(null);
        return organizationModels;
    }

    private List<TreeOrganizationModel> getTreeOrganization(TreeOrganizationModel organizationModel){
        int pid = 0;
        if(organizationModel != null){
            pid = organizationModel.getId();
        }
        RbacOrganizationEntity condition = new RbacOrganizationEntity();
        condition.setPid(pid);
        List<RbacOrganizationEntity> organizationEntities = rbacOrganizationMapper.select(condition);
        List<TreeOrganizationModel> organizationModels = new ArrayList<>();
        if(organizationEntities != null && !organizationEntities.isEmpty()){
            for (RbacOrganizationEntity organizationEntity : organizationEntities){
                TreeOrganizationModel treeOrganizationModel = new TreeOrganizationModel();
                BeanUtils.copyProperties(organizationEntity,treeOrganizationModel);
                organizationModels.add(treeOrganizationModel);
                getTreeOrganization(treeOrganizationModel);
            }

            if (organizationModel != null) {
                organizationModel.setChildren(organizationModels);
            }

        }
        return organizationModels;
    }


    public OrganizationBaseInfoModel get(int id){
        RbacOrganizationEntity organizationEntity = rbacOrganizationMapper.selectByPrimaryKey(id);
        OrganizationBaseInfoModel organizationBaseInfoModel = new OrganizationBaseInfoModel();
        BeanUtils.copyProperties(organizationEntity,organizationBaseInfoModel);
        return organizationBaseInfoModel;
    }

    public void add(CreateOrganizationModel organizationModel){
        RbacOrganizationEntity organizationEntity = new RbacOrganizationEntity();
        BeanUtils.copyProperties(organizationModel,organizationEntity);
        rbacOrganizationMapper.insert(organizationEntity);
    }

    public void delete(int id){
        RbacOrganizationEntity menuEntity = rbacOrganizationMapper.selectByPrimaryKey(id);
        rbacOrganizationMapper.delete(menuEntity);
    }

    public void update(UpdateOrganizationModel organizationModel){
        RbacOrganizationEntity organizationEntity = rbacOrganizationMapper.selectByPrimaryKey(organizationModel.getId());
        BeanUtils.copyProperties(organizationModel,organizationEntity);
        rbacOrganizationMapper.updateByPrimaryKeySelective(organizationEntity);
    }


   public List<RbacOrganizationEntity> findOrganizationByUserId(int userId){
        return rbacOrganizationMapper.findOrganizationByUserId(userId);
    }
}
