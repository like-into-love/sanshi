package com.sanshi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sanshi.result.PictureUploadResult;
import com.sanshi.service.PictureService;

@Controller
public class PictureController {

	@Autowired
	PictureService pictureService;

	/**
	 * 图片上传
	 * 
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("pic/upload")
	@ResponseBody
	public PictureUploadResult upload(MultipartFile uploadFile) {
		if (!uploadFile.isEmpty()) {
			// 得到文件名
			System.out.println(uploadFile.getOriginalFilename());
			PictureUploadResult result;
			try {
				result = pictureService.uploadPicture(uploadFile.getBytes(), uploadFile.getOriginalFilename());
				// 返回图片路径
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return PictureUploadResult.error("上传错误");

	}

}
