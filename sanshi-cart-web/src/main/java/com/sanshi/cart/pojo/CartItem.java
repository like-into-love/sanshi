package com.sanshi.cart.pojo;

public class CartItem {
    private Long itemId;
    private String title;
    private Integer num;
    private String image;
    private Long price;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "itemId=" + itemId +
                ", title='" + title + '\'' +
                ", num=" + num +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
