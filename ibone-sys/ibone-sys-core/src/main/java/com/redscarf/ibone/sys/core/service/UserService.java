package com.redscarf.ibone.sys.core.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.redscarf.ibone.common.exception.IboneException;
import com.redscarf.ibone.common.model.common.AssignPermissionModel;
import com.redscarf.ibone.common.model.common.PageRequest;
import com.redscarf.ibone.common.model.user.*;
import com.redscarf.ibone.common.utils.PasswordUtils;
import com.redscarf.ibone.sys.api.dto.ThirdPartyName;
import com.redscarf.ibone.sys.api.dto.request.ChangePasswordRequestDTO;
import com.redscarf.ibone.sys.api.dto.request.GithubUserLoginRequestDTO;
import com.redscarf.ibone.sys.api.dto.request.ThirdPartyUserLoginRequestDTO;
import com.redscarf.ibone.sys.api.dto.response.MenuInfoResponseDTO;
import com.redscarf.ibone.sys.api.dto.response.UserInfoResponseDTO;
import com.redscarf.ibone.sys.api.dto.response.UserSecurityQuestionsResponseDTO;
import com.redscarf.ibone.sys.core.mapper.*;
import com.redscarf.ibone.sys.core.model.po.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class UserService {
    @Autowired
    RbacUserMapper userMapper;

    @Autowired
    RbacSystemMapper systemMapper;

    @Autowired
    RbacMenuMapper menuMapper;

    @Autowired
    RbacUserRoleMapper userRoleMapper;

    @Autowired
    RbacRoleMapper roleMapper;

    @Autowired
    RbacPermissionMapper permissionMapper;

    @Autowired
    RbacOrganizationMapper organizationMapper;

    @Autowired
    RbacUserSecurityQuestionsMapper userSecurityQuestionsMapper;

    @Autowired
    GithubUserMapper githubUserMapper;


    /**
     * 查询用户详情
     * 1、用户基本信息
     * 2、用户权限
     * 3、用户角色
     * 4、用户菜单
     * 注：如果没有传服务名，则不加载用户菜单
     * @param username 用户名
     * @param serverName 服务名
     * @return 用户详细信息
     */
    public UserInfoResponseDTO getUserDetailByNameAndServerName(String username, String serverName) {
        UserInfoResponseDTO userModel = new UserInfoResponseDTO();
        Set<String> permissions = new HashSet<String>();
        Set<String> roles = new HashSet<String>();

        RbacUserEntity conditionUser = new RbacUserEntity();
        conditionUser.setUsername(username);
        RbacUserEntity userEntity = userMapper.selectOne(conditionUser);


        //用户角色
        List<RbacRoleEntity> roleEntities = roleMapper.findRolesByUserId(userEntity.getId());
        if(roleEntities != null && !roleEntities.isEmpty()){
            for(RbacRoleEntity roleEntity : roleEntities){
                roles.add(roleEntity.getName());

                //角色对应的权限
                List<RbacPermissionEntity> permissionEntities = permissionMapper.findPermissionsByRoleId(roleEntity.getId());
                if(permissionEntities != null && !permissionEntities.isEmpty()){
                    for (RbacPermissionEntity permissionEntity : permissionEntities){
                        permissions.add(permissionEntity.getPermissionValue());
                    }
                }
            }
        }

        //用户权限
        List<RbacPermissionEntity> permissionEntities =  permissionMapper.findPermissionsByUserId(userEntity.getId());
        if(permissionEntities != null && !permissionEntities.isEmpty()){
            for(RbacPermissionEntity permissionEntity : permissionEntities){
                permissions.add(permissionEntity.getPermissionValue());
            }
        }



        BeanUtils.copyProperties(userEntity,userModel,"menus");
        userModel.setPermissions(permissions);
        userModel.setRoles(roles);


        //如果不包含服务名，则不加载菜单信息
        if(!StringUtils.isBlank(serverName)){
            //解析前用户拥有的菜单
            List<MenuInfoResponseDTO> menuList = new ArrayList<>();
            List<RbacMenuEntity> correctMenuList = new ArrayList<>();

            RbacSystemEntity condition = new RbacSystemEntity();
            condition.setName(serverName);
            RbacSystemEntity systemEntity = systemMapper.selectOne(condition);
            if(systemEntity == null){
                return userModel;
            }
            List<RbacUserEntity> userCondition = new ArrayList<>();
            userCondition.add(userEntity);

            //获取用户和对应角色拥有的系统菜单
            int[] rolesIds = getRolesIds(roleEntities);
            int[] userIds = getUserIds(userCondition);
            List<RbacMenuEntity> roleMenus = menuMapper.findDistinctByRolesInAndPidAndSystemIdOrderByOrdersDesc(rolesIds,0,systemEntity.getId());
            List<RbacMenuEntity> userMenus = menuMapper.findDistinctByUsersInAndPidAndSystemIdOrderByOrdersDesc(userIds,0,systemEntity.getId());
            correctMenuList.addAll(roleMenus);
            correctMenuList.addAll(userMenus);

            for (RbacMenuEntity menuEntity : correctMenuList){
                MenuInfoResponseDTO menu = new MenuInfoResponseDTO();
                BeanUtils.copyProperties(menuEntity,menu);
                if(isContains(menuList,menu)){
                    continue;
                }
                List<RbacMenuEntity> childRoleMenus = menuMapper.findDistinctByRolesInAndPidAndSystemIdOrderByOrdersDesc(rolesIds,menuEntity.getId(),systemEntity.getId());
                List<RbacMenuEntity> childUserMenus = menuMapper.findDistinctByUsersInAndPidAndSystemIdOrderByOrdersDesc(userIds,menuEntity.getId(),systemEntity.getId());
                List<RbacMenuEntity> childMenus = new ArrayList<>();
                childMenus.addAll(childRoleMenus);
                childMenus.addAll(childUserMenus);

                if(!childMenus.isEmpty()){
                    List<MenuInfoResponseDTO> childMenuList = new ArrayList<>();
                    for (RbacMenuEntity childMenuEntity : childMenus){
                        MenuInfoResponseDTO childMenu = new MenuInfoResponseDTO();
                        BeanUtils.copyProperties(childMenuEntity,childMenu);
                        if(isContains(childMenuList,childMenu)){
                            continue;
                        }
                        childMenuList.add(childMenu);

                    }
                    Collections.sort(childMenuList);
                    menu.setChildMenus(childMenuList);
                }

                menuList.add(menu);

            }
            Collections.sort(menuList);
            userModel.setMenus(menuList);
        }

        return userModel;
    }


    /**
     * 获取用户实体
     * @param username
     * @return
     */
    public UserBaseInfoModel findByUserName(String username){
        RbacUserEntity condition = new RbacUserEntity();
        condition.setUsername(username);
        RbacUserEntity userEntity = userMapper.selectOne(condition);
        if(userEntity == null){
            throw new IboneException("没有找到用户");
        }
        UserBaseInfoModel userBaseInfoModel = new UserBaseInfoModel();
        BeanUtils.copyProperties(userEntity,userBaseInfoModel);
        return userBaseInfoModel;
    }



    private boolean isContains(List<MenuInfoResponseDTO> menuEntities, MenuInfoResponseDTO menu){
        for (MenuInfoResponseDTO rbacMenuEntity : menuEntities){
            if(menu.getId() == rbacMenuEntity.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * 新增用户
     * @param userModel
     */
    public void save(CreateUserModel userModel){
        RbacUserEntity userEntity = new RbacUserEntity();
        BeanUtils.copyProperties(userModel,userEntity);
        //使用时间撮作为盐值
        String salt = System.currentTimeMillis() + "";
        userEntity.setSalt(salt);
        userEntity.setPassword(PasswordUtils.getMd5PasswordWithSalt(userEntity.getPassword(),salt));


        userMapper.insert(userEntity);
    }

    /**
     * 更新用户
     * @param userModel
     */
    public void update(UpdateUserModel userModel){
        RbacUserEntity userEntity = userMapper.selectByPrimaryKey(userModel.getId());
        if(userEntity == null){
            throw new IboneException("没有找到用户");
        }

        //不修改密码
        BeanUtils.copyProperties(userModel,userEntity,"password");

        userMapper.updateByPrimaryKeySelective(userEntity);
    }

    /**
     * 删除用户
     * @param ids
     */
    public void delete(String ids){
        String[] idArray =  ids.split(",");
        for (String id:idArray){
            if(StringUtils.isBlank(id)){
                continue;
            }
            userMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    public RbacUserEntity findById(int id){
        RbacUserEntity userEntity = userMapper.selectByPrimaryKey(id);
        if(userEntity == null){
            throw new IboneException("没有找到用户");
        }
        return userEntity;
    }

    /**
     * 分配角色
     *
     * 1、全部删除
     * 2、重新赋值
     * @param assignRoleModel
     */
    public void assignRole(AssignRoleModel assignRoleModel){
        RbacUserEntity userEntity = userMapper.selectByPrimaryKey(assignRoleModel.getUserId());
        List<RbacRoleEntity> roleEntities = null;
        if(assignRoleModel.getUserRole() != null && assignRoleModel.getUserRole().length > 0){
            roleEntities = roleMapper.findByIdIn(assignRoleModel.getUserRole());
        }
        //持久化
//        userEntity.setRoles(roleEntities);
        for (RbacRoleEntity roleEntity : roleEntities) {
            userMapper.insertUserRole(userEntity.getId(),roleEntity.getId());
        }

    }

    /**
     * 分页查询
     * @return
     */
    public PageInfo<RbacUserEntity> findPage(String searchKey, PageRequest pageRequest){
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getSize());
        List<RbacUserEntity> userList = userMapper.findPageByUserNameAndRealNameAndPhoneAndEmail(searchKey);
        //分页查找
        return new PageInfo<>(userList);
    }

    /**
     * 分配菜单
     * @param assignMenuModel
     */
    public void assignMenu(AssignMenuModel assignMenuModel){
        //持久化
        //首先删除用户在该系统下的所有菜单
        RbacUserEntity userEntity = this.findById(assignMenuModel.getUserId());
        List<RbacMenuEntity> menuEntities = menuMapper.findMenusByUserId(userEntity.getId());
        if(menuEntities != null && !menuEntities.isEmpty()){
            for (int i = 0;i < menuEntities.size(); i++){
                RbacMenuEntity menuEntity = menuEntities.get(i);
                if(menuEntity.getSystemId() == assignMenuModel.getSystemId()){
                    userMapper.deleteUserMenu(userEntity.getId(),menuEntity.getId());
                    menuEntities.remove(menuEntity);
                    i--;
                }
            }
        }

        //然后插入用户菜单
        if(assignMenuModel.getUserMenu() != null && assignMenuModel.getUserMenu().length > 0){
            List<RbacMenuEntity> newMenus = menuMapper.findByIdIn(assignMenuModel.getUserMenu());
            menuEntities.addAll(newMenus);
            for (RbacMenuEntity newMenu : newMenus) {
                userMapper.insertUserMenu(userEntity.getId(),newMenu.getId());
            }
        }
    }

    /**
     * 分配权限
     * @param permissionModel
     */
    public void assignPermission(AssignPermissionModel permissionModel){
        //持久化
        //首先删除该系统下所有菜单
        RbacUserEntity userEntity = userMapper.selectByPrimaryKey(permissionModel.getId());
        List<RbacPermissionEntity> permissionEntities = permissionMapper.findPermissionsByUserId(userEntity.getId());
        if(permissionEntities != null && !permissionEntities.isEmpty()){
            for (int i = 0;i < permissionEntities.size(); i++){
                RbacPermissionEntity permissionEntity = permissionEntities.get(i);
                if(permissionEntity.getSystemId() == permissionModel.getSystemId()){
                    userMapper.deleteUserPermission(userEntity.getId(),permissionEntity.getId());
                    permissionEntities.remove(permissionEntity);
                    i--;
                }
            }
        }

        //然后插入权限
        if(permissionModel.getPermission() != null && permissionModel.getPermission().length > 0){
            List<RbacPermissionEntity> newPermissions = permissionMapper.findByIdIn(permissionModel.getPermission());
            permissionEntities.addAll(newPermissions);
            for (RbacPermissionEntity newPermission : newPermissions) {
                userMapper.insertUserPermission(userEntity.getId(),newPermission.getId());
            }
        }
    }

    /**
     * 分配组织机构
     */
    public void assignOrganization(AssignOrganizationModel assignOrganizationModel){
        //持久化
        //首先删除用户在该系统下的所有菜单
        RbacUserEntity userEntity = this.findById(assignOrganizationModel.getUserId());
        List<RbacOrganizationEntity> organizationEntities = organizationMapper.findOrganizationByUserId(userEntity.getId());
        for (RbacOrganizationEntity organizationEntity : organizationEntities) {
            userMapper.deleteUserOrganization(userEntity.getId(),organizationEntity.getId());
        }
        organizationEntities.clear();

        //然后插入用户菜单
        if(assignOrganizationModel.getUserOrganization() != null && assignOrganizationModel.getUserOrganization().length > 0){
            List<RbacOrganizationEntity> newOganizations = organizationMapper.findByIdIn(assignOrganizationModel.getUserOrganization());
            organizationEntities.addAll(newOganizations);
            for (RbacOrganizationEntity newOganization : newOganizations) {
                userMapper.insertUserOrganization(userEntity.getId(),newOganization.getId());
            }
        }

    }


    public List<UserBaseInfoModel> getUserBaseInfos(List<RbacUserEntity> userEntities){
        List<UserBaseInfoModel> userBaseInfoModelList = new ArrayList<>();
        if(userEntities == null || userEntities.isEmpty()){
            return userBaseInfoModelList;
        }
        for (RbacUserEntity userEntity : userEntities){
            UserBaseInfoModel userBaseInfoModel = new UserBaseInfoModel();
            BeanUtils.copyProperties(userEntity,userBaseInfoModel);
            userBaseInfoModelList.add(userBaseInfoModel);
        }
        return userBaseInfoModelList;
    }

    /**
     * 修改密码
     * @param modifyPasswordModel
     */
    public void modifyPassword(ModifyPasswordModel modifyPasswordModel){
        RbacUserEntity userEntity = userMapper.selectByPrimaryKey(modifyPasswordModel.getId());
        if(userEntity == null){
            throw new IboneException("没有找到用户");
        }
        //使用时间撮作为盐值
        String salt = System.currentTimeMillis() + "";
        userEntity.setSalt(salt);
        userEntity.setPassword(PasswordUtils.getMd5PasswordWithSalt(modifyPasswordModel.getPassword(),salt));
        userMapper.insert(userEntity);
    }

    /**
     * 获取用户安全问题
     * @param username
     * @return
     */
    public List<UserSecurityQuestionsResponseDTO> findUserSecurityQuestions(String username){
        UserBaseInfoModel userBaseInfoModel = findByUserName(username);
        int userId = userBaseInfoModel.getId();
        List<RbacUserSecurityQuestionsEntity> list = userSecurityQuestionsMapper.findByUserId(userId);
        if(list == null || list.isEmpty()){
            return null;
        }
        List<UserSecurityQuestionsResponseDTO> responseDTOList = new ArrayList<>();
        for(RbacUserSecurityQuestionsEntity entity:list){
            UserSecurityQuestionsResponseDTO responseDTO = new UserSecurityQuestionsResponseDTO();
            BeanUtils.copyProperties(entity,responseDTO);
            responseDTOList.add(responseDTO);
        }
        return responseDTOList;
    }


    /**
     * 修改密码
     * @param changePasswordRequestDTO
     */
    public void modifyPassword(ChangePasswordRequestDTO changePasswordRequestDTO){
        RbacUserEntity userEntity = userMapper.findByUsername(changePasswordRequestDTO.getUsername());
        if(userEntity == null){
            throw new IboneException("没有找到用户");
        }
        //使用时间撮作为盐值
        String salt = System.currentTimeMillis() + "";
        userEntity.setSalt(salt);
        userEntity.setPassword(PasswordUtils.getMd5PasswordWithSalt(changePasswordRequestDTO.getPassword(),salt));
        userMapper.insert(userEntity);
    }

    /**
     * 第三方登录
     * @param thirdPartyUserLoginRequestDTO
     */
    public void thirdPartyLogin(ThirdPartyUserLoginRequestDTO thirdPartyUserLoginRequestDTO){
        if(thirdPartyUserLoginRequestDTO.getThirdPartyName() == ThirdPartyName.GITHUB){
            GithubUserLoginRequestDTO requestDTO = (GithubUserLoginRequestDTO)thirdPartyUserLoginRequestDTO;
            GithubUserEntity githubUserEntity = githubUserMapper.findByGithubId(Long.parseLong(requestDTO.getId()));
            if(githubUserEntity == null){
                githubUserEntity = new GithubUserEntity();

                //如果是第一次登录，默认添加用户并赋予guest角色
                String username = ThirdPartyName.GITHUB.toString().toUpperCase() + "_" + requestDTO.getId();
                CreateUserModel createUserModel = new CreateUserModel();
                createUserModel.setAvatar(requestDTO.getAvatarUrl());
                createUserModel.setEmail(requestDTO.getEmail());
                createUserModel.setRealname(requestDTO.getName());
                createUserModel.setUsername(username);
                createUserModel.setPassword(username);
                save(createUserModel);

                RbacUserEntity userEntity = userMapper.findByUsername(username);
                List<RbacRoleEntity> roleEntities = roleMapper.findByName("guest");
                if(roleEntities != null && !roleEntities.isEmpty()){
                    //持久化
//                    userEntity.setRoles(roleEntities);
                    for (RbacRoleEntity roleEntity : roleEntities) {
                        userMapper.insertUserRole(userEntity.getId(),roleEntity.getId());
                    }

                }
                githubUserEntity.setUserId(userEntity.getId());
            }
            githubUserEntity.setAvatarUrl(requestDTO.getAvatarUrl());
            githubUserEntity.setBlog(requestDTO.getBlog());
            githubUserEntity.setCompany(requestDTO.getCompany());
            githubUserEntity.setEmail(requestDTO.getEmail());
            githubUserEntity.setGithubId(Long.parseLong(requestDTO.getId()));
            githubUserEntity.setHtmlUrl(requestDTO.getHtmlUrl());
            githubUserEntity.setLogin(requestDTO.getLogin());
            githubUserEntity.setName(requestDTO.getName());
            githubUserEntity.setNodeId(requestDTO.getNodeId());


            githubUserMapper.insert(githubUserEntity);
        }

    }

    private int[] getRolesIds(List<RbacRoleEntity> roles){
        if( roles != null ){
            int[] roleids = new int[roles.size()];
            for (int i = 0; i < roles.size(); i++) {
                roleids[i] = roles.get(i).getId();
            }
            return roleids;
        }
       return new int[0];
    }
    private int[] getUserIds(List<RbacUserEntity> users){
        if( users != null ){
            int[] roleids = new int[users.size()];
            for (int i = 0; i < users.size(); i++) {
                roleids[i] = users.get(i).getId();
            }
            return roleids;
        }
        return new int[0];
    }

}
