package com.sanshi.mapper;

import com.sanshi.pojo.TbItemDesc;

public interface TbItemDescMapper {
    /**
     * 添加新的商品描述
     *
     * @param itemDesc
     */
    void insertTbItemDesc(TbItemDesc itemDesc);

    /**
     * 根据id查询商品详情
     *
     * @param itemId
     * @return
     */
    TbItemDesc getTbItemDescById(long itemId);

}