package com.sanshi.result;

import java.io.Serializable;

/*
 * 记录上传的图片信息。
 * 
 */
public class PictureUploadResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 成功时 { "error" : 0, "url" : "http://www.example.com/path/to/file.ext" }
	 * 失败时 { "error" : 1, "message" : "错误信息" }
	 */
	private int error;
	private String massage;
	private String url;

	public PictureUploadResult(int error, String massage, String url) {
		super();
		this.error = error;
		this.massage = massage;
		this.url = url;
	}

	public static PictureUploadResult ok(String url) {
		return new PictureUploadResult(0, null, url);
	}

	public static PictureUploadResult error(String massage) {
		return new PictureUploadResult(0, massage, null);
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PictureUploadResult [error=" + error + ", massage=" + massage + ", url=" + url + "]";
	}

	public PictureUploadResult() {
		super();
	}

}
