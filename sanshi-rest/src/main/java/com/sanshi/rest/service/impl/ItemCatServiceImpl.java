package com.sanshi.rest.service.impl;

import com.sanshi.mapper.TbItemCatMapper;
import com.sanshi.pojo.TbItemCat;
import com.sanshi.rest.pojo.CatNode;
import com.sanshi.rest.pojo.CatResult;
import com.sanshi.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        catResult.setData(getListByParentId(0));
        return catResult;
    }

    private List<?> getListByParentId(long parentId) {
        //执行查询
        List<TbItemCat> catList = tbItemCatMapper.getCatList(parentId);
        List<Object> result = new ArrayList<Object>();
        int count = 0;
        for (TbItemCat tbItemCat : catList) {
            //判断是否叶子目录
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                // products/1.html
                catNode.setUrl("products/" + tbItemCat.getId() + ".html");

                // 第一层目录带链接
                // <a href='/products/1.html'>图书、音像、电子书刊</a>
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }// 递归查询出子目录
                catNode.setitems(getListByParentId(tbItemCat.getId()));
                result.add(catNode);
                count++;
                // 页面只能显示14条数据，不能显示多了
                if (count >= 14) {
                    break;
                }
                // 取出叶子节点
            } else {
                /// products/52.html|科技
                result.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName() + "");
            }
        }
        return result;
    }
}
