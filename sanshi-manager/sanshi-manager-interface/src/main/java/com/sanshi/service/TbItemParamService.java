package com.sanshi.service;

import com.sanshi.result.TaotaoResult;

public interface TbItemParamService {
	
	/**
	 * 根据选择类目的商品id看是否查询出规格模板，取到了说明此商品分类的规格模板已经添加提示不能添加 
	 * 如果没有取到就正常添加
	 */
	public TaotaoResult selectTbItemParam(long id);
	
	
	//保存创建模板 接收商品分类id和模板参数json
	public TaotaoResult insertItemParam(long cid, String paramData);

}
