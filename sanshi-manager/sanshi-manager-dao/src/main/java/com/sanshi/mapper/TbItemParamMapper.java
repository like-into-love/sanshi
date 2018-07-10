package com.sanshi.mapper;

import com.sanshi.pojo.TbItemParam;

import java.util.List;

public interface TbItemParamMapper {

	/**
	 * 根据选择类目的商品id看是否查询出规格模板，取到了说明此商品分类的规格模板已经添加提示不能添加 
	 * 如果没有取到就正常添加
	 */
	public List<TbItemParam> selectTbItemParam(long id);
	/**
	 * 
	 * @param tbItemParam 商品规则参数
	 */
	void insertItemParam(TbItemParam tbItemParam);

}