package com.sanshi.item.pojo;

import com.sanshi.pojo.TbItem;

public class Item extends TbItem {

    private String[] images;

    public void setImages(String[] images) {
        this.images = images;
    }

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setBarcode(tbItem.getBarcode());
        this.setPrice((tbItem.getPrice()));
        this.setCid(tbItem.getCid());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
        this.setSellPoint(tbItem.getSellPoint());
        this.setNum(tbItem.getNum());
        this.setStatus(tbItem.getStatus());
        if (tbItem.getImage() != null && !"".equals(tbItem.getImage())) {
            this.setImages(tbItem.getImage().split(","));
        } else {
            this.setImages(null);
        }
    }
    public String[] getImages() {
        return images;
    }
}
