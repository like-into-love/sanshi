package com.sanshi.rest.service;

import com.sanshi.rest.pojo.CatResult;

public interface ItemCatService {
    /**
     * 前台与页面查询分类详情
     *
     * @return CatResult 对象
     */
    CatResult getItemCatList();
}
