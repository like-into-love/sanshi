package com.sanshi.pojo;

import java.io.Serializable;

public class TbItemFreemarker implements Serializable {
    private int id;
    private String itemFreemarkerSrc;
    private Long itemId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemFreemarkerSrc() {
        return itemFreemarkerSrc;
    }

    public void setItemFreemarkerSrc(String itemFreemarkerSrc) {
        this.itemFreemarkerSrc = itemFreemarkerSrc;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "TbItemFreemarker{" +
                "id=" + id +
                ", itemFreemarkerSrc='" + itemFreemarkerSrc + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
