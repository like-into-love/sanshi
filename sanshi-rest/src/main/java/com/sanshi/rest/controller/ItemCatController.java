package com.sanshi.rest.controller;

import com.sanshi.rest.pojo.CatResult;
import com.sanshi.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemCatController {
    @Autowired
    ItemCatService itemCatService;

    /*	@RequestMapping(value = "itemcat/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody // callback页面回调函数的名称
	public String getItemCatList1(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		// 选取前14类商品 (14)初始容量；
		List<Object> newData = new ArrayList<Object>(14);
		List<?> data = catResult.getData();
		for (int i = 0; i < 14; i++) {
			newData.add(data.get(i));
		}
		catResult.setData(newData);
		return callback + "(" + JsonUtils.objectToJson(catResult) + ")";
	}
*/
    @RequestMapping(value = "itemcat/all")
    @ResponseBody
    public Object getItemCatList(String callback) {
        CatResult catResult = itemCatService.getItemCatList();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
        // 页面回调函数
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;

    }
}
