package com.sanshi.search.service;

import com.sanshi.result.TaotaoResult;

public interface SolrItemService {
    /**
     * SolrItem 取出数据导入索引文档库
     *
     * @return
     */
     TaotaoResult importAllItems();

    /**
     * 索引同步，根据消息队列接收的itemId条件，查询新添加的商品，加入到索引库
     *
     * @return
     */
     TaotaoResult importByIdItems(long itemId);


}
