package com.sanshi.service;

import com.sanshi.result.PictureUploadResult;

public interface PictureService {
	public PictureUploadResult uploadPicture(byte[] bytes, String name);

}
