package com.sanshi.pojo;

import java.io.Serializable;

/**
 * solr 显示的内容
 *
 * @author wu_lei
 */
public class SolrItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String sellPoint;
    private Double price;
    private String image;
    private String categoryName;
    private String itemDesc;
    private String[] images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Override
    public String toString() {
        return "SolrItem [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", image="
                + image + ", categoryName=" + categoryName + ", itemDesc=" + itemDesc + "]";
    }

    public String[] getImages() {
        if (!"".equals(image) && image != null) {
            images = image.split(",");
        }
        return images;
    }
}
