package com.bysj.tags;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public class ImageUtil {
	/**
	 * ֱ��ָ��ѹ����Ŀ�ߣ� (�ȱ���ԭ�ļ�����ѹ�����ϴ�) Ҽ����Ŀ�����ڶ�ά��ѹ��
	 * 
	 * @param oldFile   Ҫ����ѹ�����ļ�ȫ·��
	 * @param width     ѹ����Ŀ��
	 * @param height    ѹ����ĸ߶�
	 * @param quality   ѹ������
	 * @param smallIcon �ļ�����СС��׺(ע�⣬���ļ���׺����),��ѹ���ļ�����yasuo.jpg,��ѹ�����ļ�����yasuo(+smallIcon).jpg
	 * @return ����ѹ������ļ���ȫ·��
	 */
	public static String zipImageFile(String oldFile, int width, int height, float quality, String smallIcon) {
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			/** �Է������ϵ���ʱ�ļ����д��� */
			Image srcFile = ImageIO.read(new File(oldFile));
			/** ��,���趨 */
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
			String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
			/** ѹ������ļ��� */
			newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
			/** ѹ��֮����ʱ���λ�� */
			FileOutputStream out = new FileOutputStream(newImage);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/** ѹ������ */
			jep.setQuality(quality, true);
			encoder.encode(tag, jep);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newImage;
	}

	/**
	 * �����ļ�����������ʱ·��(�����ļ��ϴ�)
	 * 
	 * @param fileName
	 * @param is
	 * @return �ļ�ȫ·��
	 */
	public static String writeFile(String fileName, InputStream is) {
		if (fileName == null || fileName.trim().length() == 0) {
			return null;
		}
		try {
			/** ���ȱ��浽��ʱ�ļ� */
			FileOutputStream fos = new FileOutputStream(fileName);
			byte[] readBytes = new byte[512];// �����С
			int readed = 0;
			while ((readed = is.read(readBytes)) > 0) {
				fos.write(readBytes, 0, readed);
			}
			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * �ȱ���ѹ���㷨�� �㷨˼�룺����ѹ��������ѹ������ѹ��ԭͼ������һ��ͼƬЧ����ӽ�ԭͼ������ͼ
	 * 
	 * @param srcFilename ԭͼ�ļ���������ͼ��ԭ�ļ�ǰƴ��small_
	 * @param path        ԭͼ�ļ�·��
	 * @param comBase     ѹ������
	 * @param scale       ѹ������(��/��)���� һ����1��
	 *                    ��scale>=1,����ͼheight=comBase,width��ԭͼ��߱���;��scale<1,����ͼwidth=comBase,height��ԭͼ��߱���
	 * @throws Exception
	 * @author shenbin
	 * @createTime 2014-12-16
	 * @lastModifyTime 2014-12-16
	 */
	public static void saveMinPhoto(String srcFilename, String path, double comBase, double scale) throws Exception {

		String suffix = SplitUploadUtil.getFileSufix(srcFilename).toLowerCase();
		if (suffix.indexOf("jpg") < 0 && suffix.indexOf("png") < 0) {
			return;
		}
		File srcFile = new File(path + srcFilename);
		String deskURL = path + "small_" + srcFilename;
		Image src = ImageIO.read(srcFile);
		int srcHeight = src.getHeight(null);
		int srcWidth = src.getWidth(null);
		int deskHeight = 0;// ����ͼ��
		int deskWidth = 0;// ����ͼ��
		double srcScale = (double) srcHeight / srcWidth;
		/** ����ͼ����㷨 */
		if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
			if (srcScale >= scale || 1 / srcScale > scale) {
				if (srcScale >= scale) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			} else {
				if ((double) srcHeight > comBase) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			}
		} else {
			deskHeight = srcHeight;
			deskWidth = srcWidth;
		}
		BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
		tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); // ������С���ͼ
		FileOutputStream deskImage = new FileOutputStream(deskURL); // ������ļ���
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
		encoder.encode(tag); // ��JPEG����
		deskImage.close();
	}

	/**
	 * 
	 * @param originalFilename ԭͼ�ļ���
	 * @param path             ԭͼ�ļ�·��
	 * @param newWidth         ָ�����
	 * @param quality
	 * @throws IOException
	 */
	public static void resize(String originalFilename, String path, int newWidth, float quality) throws IOException {
		String suffix = SplitUploadUtil.getFileSufix(originalFilename).toLowerCase();
		if (suffix.indexOf("jpg") < 0 && suffix.indexOf("png") < 0) {
			return;
		}
		File originalFile = new File(path + originalFilename);
		File resizedFile = new File(path + "small_" + originalFilename);
		if (quality > 1) {
			throw new IllegalArgumentException("Quality has to be between 0 and 1");
		}

		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image i = ii.getImage();
		Image resizedImage = null;

		int iWidth = i.getWidth(null);
		int iHeight = i.getHeight(null);

		if (iWidth < newWidth) {
			newWidth = iWidth;
		}
		if (iWidth > iHeight) {
			resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
		} else {
			resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
		}

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
				BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor,
				0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		// Write the jpeg to a file.
		FileOutputStream out = new FileOutputStream(resizedFile);

		// Encodes image as a JPEG data stream
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
		param.setQuality(quality, true);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(bufferedImage);
	} // Example usage

	public static void main(String args[]) throws Exception {
		System.out.println(new Date().getTime());
		ImageUtil.saveMinPhoto("testtest.png", "e:\\", 300, 1);
		System.out.println(new Date().getTime());
		ImageUtil.resize("testtest.png", "e:\\", 300, 1);
		System.out.println(new Date().getTime());
	}
}
