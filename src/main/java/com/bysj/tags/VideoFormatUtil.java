package com.bysj.tags;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoFormatUtil {

	public static void main(String[] args) {
		convert2Flv("E:/kspace/ffmpeg/ffmpeg.exe","E:/kspace/tswh/lbwh/1513560894898_1_5495.avi",
				"E:/kspace/tswh/lbwh/flv_1513560894898_1_5495.flv","E:/kspace/tswh/lbwh/img_1513560894898_1_5495.jpg");
	}
	public static boolean convert2Flv(String ffmpegPath, String inputFile, String outputVideoFile,
			String outputImageFile) {
		if (!checkfile(inputFile)) {
			System.out.println(inputFile + " is not file");
			return false;
		}
		List<String> commend = new ArrayList<String>();

		commend.add(ffmpegPath);
		commend.add("-i");
		commend.add(inputFile);
		commend.add("-y -f image2 -ss 5 -t 0.001 -s 138x76");
		commend.add(outputImageFile);
		commend.add("-ab 56 -ar 22050 -b 500 -r 15 -qscale 11");
		commend.add(outputVideoFile);

		StringBuffer test = new StringBuffer();
		for (int i = 0; i < commend.size(); i++) {
			test.append(commend.get(i) + " ");
		}

		System.out.println(test);

		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 截屏
	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	public static boolean screensHot(String ffmpegPath,String inputFile, String outputFile) {
		if (!checkfile(inputFile)) {
			System.out.println(inputFile + " is not file");
			return false;
		}
		// ffmpeg.exe -i xxx.mp4 -y -f image2 -t 2 -ss 50 -s 64*320 xxx.jpg
		List<String> commend = new ArrayList<String>();
		// commend.add("ffmpeg");
		commend.add(ffmpegPath);
		commend.add("-i");

		commend.add(inputFile);
		commend.add("-y");

		commend.add("-f");
		commend.add("image2");

		commend.add("-t");
		commend.add("2");
		// 第50秒
		commend.add("-ss");
		commend.add("5");
		// 图片大小
		commend.add("-s");
		commend.add("138*76");

		commend.add(outputFile);

		StringBuffer test = new StringBuffer();
		for (int i = 0; i < commend.size(); i++) {
			test.append(commend.get(i) + " ");
		}

		System.out.println(test);

		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 检查文件是否存在
	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}
}
