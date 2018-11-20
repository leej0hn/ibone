package com.redscarf.ibone.sys.admin.controller;

import com.github.pagehelper.PageInfo;
import com.redscarf.ibone.common.model.ListModel;
import com.redscarf.ibone.common.model.common.PageRequest;
import com.redscarf.ibone.common.model.system.CreateSystemModel;
import com.redscarf.ibone.common.model.system.UpdateSystemModel;
import com.redscarf.ibone.common.ui.result.Result;
import com.redscarf.ibone.common.utils.ResultUtils;
import com.redscarf.ibone.sys.core.model.po.RbacSystemEntity;
import com.redscarf.ibone.sys.core.service.SystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequiresPermissions("sys:system:read")
    @RequestMapping("/index")
    public String index(){
        return "pages/system/index";
    }

    @RequiresPermissions("sys:system:read")
    @RequestMapping("/list")
    @ResponseBody
    public Result list(ListModel listModel){
//        PageRequest pageRequest = new PageRequest(listModel.getPageNumber()-1,listModel.getPageSize(), Sort.Direction.fromString(listModel.getSortOrder()),listModel.getSortName());
        //分页查找
        PageRequest pageRequest = new PageRequest(listModel.getPageNumber(),listModel.getPageSize());
        PageInfo<RbacSystemEntity> page = systemService.findPage(listModel.getSearchText(),pageRequest);
        return ResultUtils.wrapSuccess(page.getTotal(),page.getList());
    }

    @RequiresPermissions("sys:system:create")
    @RequestMapping("/create")
    @ResponseBody
    public Result create(@Validated CreateSystemModel createSystemModel, BindingResult bindingResult){
        systemService.save(createSystemModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:system:create")
    @RequestMapping("/toCreate")
    public String toCreate(){
        return "pages/system/create";
    }

    @RequiresPermissions("sys:system:update")
    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id")String id, ModelMap modelMap){
        RbacSystemEntity systemEntity = systemService.get(Integer.parseInt(id));
        modelMap.put("system",systemEntity);
        return "pages/system/update";
    }

    @RequiresPermissions("sys:system:update")
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Validated UpdateSystemModel updateSystemModel, BindingResult bindingResult){
        systemService.update(updateSystemModel);
        return ResultUtils.wrapSuccess();
    }


    @RequiresPermissions("sys:system:delete")
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public Result delete(@PathVariable("ids")String ids){
        systemService.delete(ids);
        return ResultUtils.wrapSuccess();
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    public Result get(@PathVariable("id")String id){
        RbacSystemEntity systemEntity = systemService.get(Integer.parseInt(id));
        return ResultUtils.wrapSuccess(systemEntity);
    }
}
