package com.sanshi.mapper;

import com.sanshi.pojo.TbItemFreemarker;

public interface TbItemFreemarkerMapper {
    /**
     * 插入模版信息
     *
     * @param tbItemFreemarker
     */
    void insertTbItemFreemarker(TbItemFreemarker tbItemFreemarker);

    /**
     * 根据商品id查询是否有模板
     *
     * @param itemId 商品id
     * @return
     */
    TbItemFreemarker getTbItemFreemarkerById(long itemId);
}
