package com.sanshi.item.listener;

import com.sanshi.item.pojo.Item;
import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
import com.sanshi.pojo.TbItemFreemarker;
import com.sanshi.service.TbItemService;
import com.sanshi.utils.FtpUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过消息队列添加修改删除来 修改可创建模版
 */
public class ItemAddListener implements MessageListener {
    @Autowired
    TbItemService tbItemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    // 注入加载配置文件的值
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USER_NAME}")
    private String FTP_USER_NAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;
    @Value("${ZERO_DIR}")
    private String ZERO_DIR;

    @Override
    public void onMessage(Message message) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");
            if (message instanceof TextMessage) {
                TextMessage age = (TextMessage) message;
                String itemId = age.getText();
                String[] split = itemId.split(",");
                System.out.println(itemId);
                System.out.println(split);
                //添加新商品个更新商品的重新生成模板
                if ("1".equals(split[1]) || "2".equals(split[1])) {
                    //商品
                    itemId = split[0];
                    TbItem tbItem = tbItemService.getTbItemById(Long.valueOf(itemId));
                    TbItemDesc tbItemDesc = tbItemService.getTbItemDescById(Long.valueOf(itemId));
                    String paramItem = tbItemService.getTbItemParamItemByid(Long.valueOf(itemId));
                    Map map = new HashMap();
                    map.put("item", new Item(tbItem));
                    map.put("itemDesc", tbItemDesc.getItemDesc());
                    map.put("itemParam", paramItem);

                    //充当零时存放目录当写到FTP服务器上面的时候删除这个文件
                    BufferedWriter bf = new BufferedWriter(new FileWriter(ZERO_DIR + itemId + ".html"));
                    template.process(map, bf);
                    bf.close();
                 /*
                 1   这里应给上传到nginx+usftpd
                 2   然后把商品id和静态模板路径保存到数据库中 保存到数据库
                 3   前台搜索页面时传来商品id然根据商品id取到路径
                    如果取到就重定向这个路径，没有取到就说明没有这个商品的静态模板，调用查询数据的信息返回给jsp页面
           */
                    BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(ZERO_DIR + itemId + ".html"));
                    boolean aStatic = false;
                    try {
                        aStatic = FtpUtils.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USER_NAME, FTP_PASSWORD, FTP_BASE_PATH,
                                "/static", itemId + ".html", inputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    //如果上传成功才写如数据库
                    if (aStatic) {
                        TbItemFreemarker tbItemFreemarker = new TbItemFreemarker();
                        tbItemFreemarker.setItemFreemarkerSrc(IMAGE_BASE_URL + "/static" + "/" + itemId + ".html");
                        tbItemFreemarker.setItemId(Long.valueOf(itemId));
                        tbItemService.addTbItemFreemarker(tbItemFreemarker);
                    }
                    //删除临时存放的文件夹
                    new File(ZERO_DIR + itemId + ".html").delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
