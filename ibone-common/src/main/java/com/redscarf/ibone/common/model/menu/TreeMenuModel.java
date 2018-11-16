package com.redscarf.ibone.common.model.menu;

import lombok.Data;

import java.util.List;

@Data
public class TreeMenuModel extends MenuBaseInfoModel {
    private List<TreeMenuModel> children;
}
