package com.sanshi.portal.pojo;

public class IndexAd {
	private String alt;
	private Integer height;
	private Integer heightB;
	private String href;
	private String src;
	private String srcB;
	private Integer width;
	private Integer widthB;

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHeightB() {
		return heightB;
	}

	public void setHeightB(Integer heightB) {
		this.heightB = heightB;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSrcB() {
		return srcB;
	}

	public void setSrcB(String srcB) {
		this.srcB = srcB;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getWidthB() {
		return widthB;
	}

	public void setWidthB(Integer widthB) {
		this.widthB = widthB;
	}

	@Override
	public String toString() {
		return "IndexAd [alt=" + alt + ", height=" + height + ", heightB=" + heightB + ", href=" + href + ", src=" + src
				+ ", srcB=" + srcB + ", width=" + width + ", widthB=" + widthB + "]";
	}

}
