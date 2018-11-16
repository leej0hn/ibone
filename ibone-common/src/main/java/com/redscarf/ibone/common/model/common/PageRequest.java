package com.redscarf.ibone.common.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:
 * @author: LeeJohn
 * @date: 2018/11/16 15:57
 */
@AllArgsConstructor
@Data
public class PageRequest {
    /**
     * 页数从1开始
     */
    private int page = 1;
    private int size = 10;
}
