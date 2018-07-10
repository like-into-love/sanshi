package com.sanshi.cms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sanshi.cms.jedis.JedisClient;
import com.sanshi.cms.service.TbContentService;
import com.sanshi.mapper.TbContentMapper;
import com.sanshi.pojo.TbContent;
import com.sanshi.result.EasyUIDataGtidResult;
import com.sanshi.result.TaotaoResult;
import com.sanshi.utils.JsonUtils;

@Service("tbContentServiceImpl")
public class TbContentServiceImpl implements TbContentService {

	@Autowired
	TbContentMapper tbContentMapper;

	@Autowired
	JedisClient jedisClient;

	@Value("${REDIS_KEY}")
	private String REDIS_KEY;

	/**
	 * 
	 * @param categoryId
	 *            分类id
	 * @return 页面需要EasyUIDataGtidResult对象里面的 row,total
	 */
	@Override
	public EasyUIDataGtidResult selectTbContentAll(long categoryId) {
		List<TbContent> tbContentAll = tbContentMapper.getTbContentAll(categoryId);
		EasyUIDataGtidResult result = new EasyUIDataGtidResult();
		result.setPage(tbContentAll.size());
		result.setRows(tbContentAll);
		return result;
	}

	/**
	 * 添加新的内容
	 * 
	 * @param tbContent
	 * @return 返回TaotaoResult
	 */
	@Override
	public TaotaoResult addTbContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		// 只添加了大广告
		tbContentMapper.insertTbContent(tbContent);
		// 缓存同步当后台添加或删除的时候，把key清空
		jedisClient.hdel(REDIS_KEY, tbContent.getCategoryId() + "");
		return TaotaoResult.ok(null);
	}

	/**
	 * 前台根据categoryId查询内容
	 */
	@Override
	public List<TbContent> getIndexAdList(long categoryId) {
		// 取出缓存
		String hget = jedisClient.hget(REDIS_KEY, categoryId + "");
		if (StringUtils.isNotBlank(hget)) {
			List<TbContent> result = JsonUtils.jsonToList(hget, TbContent.class);
			return result;
		}

		List<TbContent> tbContent = tbContentMapper.getTbContentAll(categoryId);
		try {
			// 添加缓存
			jedisClient.hset(REDIS_KEY, categoryId + "", JsonUtils.objectToJson(tbContent));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tbContent;
	}

	/**
	 * 根据id删除这条内容
	 * 
	 * @param id
	 */
	@Override
	public TaotaoResult delTbContent(long id) {
		tbContentMapper.deleteTbContent(id);
		return TaotaoResult.ok(null);
	}

}
