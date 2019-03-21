package com.bysj.tags;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class SplitUploadUtil {
	/**
	 * 分片上传文件
	 * 
	 * @param fileItem
	 * @param path
	 * @param noticeId
	 * @return
	 */
	public static Map<String, String> upLoadFile2(FileItem fileItem, Integer index, Integer total, String path,
			String fileName, Integer type) throws Exception {

		if (fileItem == null)
			return null;
		Map<String, String> map = new HashMap<>();
		File file = new File(path + getFilePrefix(fileName) + "_" + index + getFileSufix(fileName));
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(fileItem.getInputStream());
			os = new BufferedOutputStream(new FileOutputStream(file, true));
			int len = 0;
			byte[] bs = new byte[8192];
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.flush();
			is.close();
			os.close();
			fileItem.delete();
			map.put(fileName, fileItem.getName());
			// 当数据块等于总块数时，将临时文件组合成原文件，并删除临时文件
			if (index.equals(total)) {
				File newfile = new File(path, fileName);
				if (newfile.exists()) {
					newfile.delete();
				}
				BufferedOutputStream outputStream = new BufferedOutputStream(
						new FileOutputStream(new File(path, fileName)));
				for (int i = 1; i <= total; i++) {
					File tempfile = new File(path + getFilePrefix(fileName) + "_" + i + getFileSufix(fileName));
					byte[] bytes = FileUtils.readFileToByteArray(tempfile);
					outputStream.write(bytes);
					outputStream.flush();
					tempfile.delete();
				}
				outputStream.flush();
				outputStream.close();
				if (type == 1) {
					ImageUtil.saveMinPhoto(fileName, path, 300, 1);
				}
			}
			return map;

		} catch (IOException e) {
			e.printStackTrace();
			return map;
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param fileItem
	 * @param path
	 * @param noticeId
	 * @return
	 */
	public Map<String, String> upLoadFile(FileItem fileItem, String path, String fileName) {

		if (fileItem == null)
			return null;
		Map<String, String> map = new HashMap<>();

		File file = new File(path, fileName);
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(fileItem.getInputStream());
			os = new BufferedOutputStream(new FileOutputStream(file, true));
			int len = 0;
			byte[] bs = new byte[8192];
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.flush();
			is.close();
			os.close();
			fileItem.delete();

			map.put(fileName, fileItem.getName());
			return map;

		} catch (IOException e) {
			e.printStackTrace();
			return map;
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Map<String, String> upLoadFile2(FileItem fileItem, String path, String fileName) {

		if (fileItem == null)
			return null;
		Map<String, String> map = new HashMap<>();

		File file = new File(path, fileName);
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(fileItem.getInputStream());
			os = new BufferedOutputStream(new FileOutputStream(file, true));
			int len = 0;
			byte[] bs = new byte[8192];
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.flush();
			is.close();
			os.close();
			fileItem.delete();

			map.put(fileName, fileItem.getName());
			return map;

		} catch (IOException e) {
			e.printStackTrace();
			return map;
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}

	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex);
	}

	public static Map<String, Object> getParameter(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<FileItem> list = new ArrayList<FileItem>();
		String contentType = request.getContentType();
		if (StringUtils.isEmpty(contentType)) {
			return null;
		}
		if (contentType.indexOf("multipart/form-data") == -1) {
			return getDataParameter(request);
		} else {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(3 * 1024 * 1024);// 单位是字节
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setHeaderEncoding("UTF-8");
			try {
				list = fileUpload.parseRequest(request);
				for (FileItem fileItem : list) {
					if (fileItem.isFormField()) {
						String name = fileItem.getFieldName();
						String value = fileItem.getString("UTF-8");
						map.put(name, value);
					} else {
						String fieldName = fileItem.getFieldName();
						map.put(fieldName, fileItem);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static Map<String, Object> getDataParameter(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String value = "";
			String strings = (String) names.nextElement();
			String[] parameterValues = request.getParameterValues(strings);
			for (int i = 0; parameterValues != null && i < parameterValues.length; i++) {
				if (i > 0) {
					value += ";";
				}
				value += parameterValues[i];
			}
			map.put(strings, value);
		}
		return map;
	}
}
