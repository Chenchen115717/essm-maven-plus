package com.bysj.controller.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bysj.tools.CommonUtils;

//文件图片处理类
@Controller
public class FileController {

	@Value("${UPLOAD_URL}")
	private String UPLOAD_URL;

	@RequestMapping(value = "/getImage")
	public void getValidationCode(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("filename") String filename) throws Exception {

		// 设置响应的类型格式为图片格式
	    response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String IMAGE_URL = UPLOAD_URL + "/" + filename;
		if (StringUtils.isBlank(filename)) {
			IMAGE_URL = request.getServletContext().getRealPath("upload_files") + "/nopicsmall.png";
		} else if (!new File(IMAGE_URL).exists()) {
			IMAGE_URL = request.getServletContext().getRealPath("upload_files") + "/" + filename;
			if (!new File(IMAGE_URL).exists()) {
				IMAGE_URL = request.getServletContext().getRealPath("upload_files") + "/nopicsmall.png";
			}
		}

		byte[] bytes = CommonUtils.toByteArray2(new File(IMAGE_URL));
		if (bytes != null) {
			response.getOutputStream().write(bytes);
		} else {
			response.setStatus(404);
		}
	}

	@RequestMapping(value = "/download")
	public void download(@RequestParam("filename") String filename, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		// 初始化文件流，提供客户端下载
		if (StringUtils.isNotBlank(filename)) {
			filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
			String FILE_URL = UPLOAD_URL + "/" + filename;
			File file = new File(FILE_URL);// 构造要下载的文件
			if (file.exists()) {
				InputStream ins = new FileInputStream(FILE_URL);// 构造一个读取文件的IO流对象
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");// 设置response内容的类型
				response.setContentLength((int) file.length());// 设置文件大小
				response.setHeader("Content-disposition",
						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			} else {
				System.out.println("下载的文件不存在");
			}
		} else {
			System.out.println("下载文件时参数错误");
		}
	}

}
