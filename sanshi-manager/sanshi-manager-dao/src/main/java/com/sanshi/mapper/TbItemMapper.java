package com.sanshi.mapper;

import com.sanshi.pojo.TbItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TbItemMapper {

    /**
     * 查询所有商品，通过翻页插件，进行分页
     */
    List<TbItem> findAllTbItem();

    /**
     * 添加商品
     *
     * @param item
     */
    void insertTbItem(TbItem item);

    /**
     * 根据id修改商品状态
     * 多个参数可以使用注解也可以用map集合,百度查@Param里面的就是参数的名字与mapper.xml里面sql的参数名一样
     */
    void updateItemStatus(@Param("status") int status, @Param("list") List<Long> id);

    /**
     * 根据商品itemId查询商品
     *
     * @param itemId
     * @return
     */
    TbItem getTbItemById(long itemId);
}