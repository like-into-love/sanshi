package com.sanshi.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sanshi.result.PictureUploadResult;
import com.sanshi.service.PictureService;
import com.sanshi.utils.FtpUtils;
import com.sanshi.utils.IDUtils;

@Service("pictureServiceImpl")
public class PictureServiceImpl implements PictureService {

	// @Value对应的属性不能为static,否则注入为null
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

	@Override
	public PictureUploadResult uploadPicture(byte[] bytes, String name) {
		InputStream input = new ByteArrayInputStream(bytes);
		// 拿到扩展名
		String ext = name.substring(name.lastIndexOf("."));
		// System.out.println(name.lastIndexOf("."));
		// System.out.println("扩展名" + ext);
		// 通过uuID生成随机文件名
		String imageName = IDUtils.genImageName();
		// 通过hascode打乱存储
		String path = IDUtils.newpath(name);
		try {
			boolean uploadFile = FtpUtils.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USER_NAME, FTP_PASSWORD, FTP_BASE_PATH,
					path, imageName + ext, input);
			if (!uploadFile) {
				PictureUploadResult.error("ftp上传失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回图片路径
		return PictureUploadResult.ok(IMAGE_BASE_URL + path + "/" + imageName + ext);
	}

}
