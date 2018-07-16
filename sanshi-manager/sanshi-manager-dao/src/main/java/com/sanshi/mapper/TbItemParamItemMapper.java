package com.sanshi.mapper;

import com.sanshi.pojo.TbItemParamItem;

public interface TbItemParamItemMapper {

    /**
     * 添加商品规格
     */

    void addTbItemParamItem(TbItemParamItem tbItemParamItem);

    /**
     * 根据商品id查询商品的规格参数
     *
     * @param itemId
     * @return TbItemParamItem
     */
    TbItemParamItem getTbItemParamItemByid(long itemId);

}