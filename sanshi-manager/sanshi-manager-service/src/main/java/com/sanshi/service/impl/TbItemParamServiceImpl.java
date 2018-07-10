package com.sanshi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanshi.mapper.TbItemParamMapper;
import com.sanshi.pojo.TbItemParam;
import com.sanshi.result.TaotaoResult;
import com.sanshi.service.TbItemParamService;

@Service("tbItemParamServiceImpl")
public class TbItemParamServiceImpl implements TbItemParamService {

	@Autowired
	TbItemParamMapper tbItemParamMapper;

	@Override
	/**
	 * 根据选择类目的商品id看是否查询出规格模板，取到了说明此商品分类的规格模板已经添加提示不能添加 如果没有取到就正常添加
	 */
	public TaotaoResult selectTbItemParam(long id) {

		List<TbItemParam> itemParam = tbItemParamMapper.selectTbItemParam(id);

		if (itemParam != null && itemParam.size() > 0) {
			// System.out.println(itemParam.get(0).getParamData());
			return TaotaoResult.ok(itemParam.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	// 保存创建模板 接收商品分类id和模板参数json
	public TaotaoResult insertItemParam(long cid, String paramData) {
		// 补全TbItemParam的信息
		TbItemParam tbParam = new TbItemParam();
		tbParam.setItemCatId(cid);
		tbParam.setParamData(paramData);
		tbParam.setCreated(new Date());
		tbParam.setUpdated(new Date());
		try {
			tbItemParamMapper.insertItemParam(tbParam);
			return TaotaoResult.resultStatus(200);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return TaotaoResult.ok();
	}

}
