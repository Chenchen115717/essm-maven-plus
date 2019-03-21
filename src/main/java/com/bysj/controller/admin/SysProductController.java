package com.bysj.controller.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bysj.beans.Product;
import com.bysj.beans.User;
import com.bysj.service.CommonService;
import com.bysj.service.ProductService;
import com.bysj.tools.CommonUtils;
import com.bysj.tools.MsgHelper;
import com.github.pagehelper.PageInfo;

//商品管理
@Controller
public class SysProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CommonService<Product> commonService;

	@Value("${UPLOAD_URL}")
	private String UPLOAD_URL;

	@RequestMapping(value = "/admin/product/index")
	public String index(HttpServletRequest request, HttpSession session, Map<String, Object> map) {
		return "admin/product/index";
	}

	@RequestMapping(value = "admin/product/getdata")
	public String getData(HttpServletRequest request, HttpSession session, @RequestParam Map<String, String> parameter,
			@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "5") Integer pageSize,
			Map<String, Object> map) throws Exception {
		String sql = "SELECT a.*,b.username FROM t_product AS a LEFT JOIN t_user AS b ON a.userid = b.id where true ";
		if (StringUtils.isNotBlank(parameter.get("title"))) {
			sql += " and (a.title like '%" + parameter.get("title") + "%' OR a.originalname like '%" + parameter.get("title") + "%')";
		}
		sql += " order by a.id desc";
		PageInfo<Product> list = commonService.getAllBySql(Product.class, sql, pageIndex, pageSize);
		map.put("list", list.getList());
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		map.put("itemTotal", list.getTotal());
		map.put("number", (pageIndex == 0 ? pageIndex : pageIndex - 1) * pageSize);
		return "/admin/product/getdata";
	}

	@RequestMapping(value = "admin/product/toadd")
	public String toAdd(HttpServletRequest request, HttpSession session, @Param("id") Integer id,
			Map<String, Object> map) {

		Product bean = new Product();
		if (id != null) {
			bean = productService.selectById(id);
		}

		map.put("bean", bean);

		return "admin/product/toadd";
	}

	@ResponseBody
	@RequestMapping(value = "admin/product/save", method = RequestMethod.POST)
	public MsgHelper save(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute("bean") Product bean)
			throws Exception {
		MsgHelper msgHelper = new MsgHelper();
		if (file != null) {
			File file2 = new File(UPLOAD_URL);
			if (!file2.exists()) {
				file2.mkdirs();
			}
			bean.setOriginalname(file.getOriginalFilename());
			String extName = CommonUtils.getExtension(file.getOriginalFilename());
			String fileName = System.currentTimeMillis() + extName;
			String savePath = UPLOAD_URL + "/" + fileName;
			bean.setImagepath(fileName);
			file.transferTo(new File(savePath));
		}
		productService.updateById(bean);
		return msgHelper;
	}

	@RequestMapping(value = "admin/product/toadd2")
	public String toAdd2(HttpServletRequest request, HttpSession session, @Param("id") Integer id,
			Map<String, Object> map) {

		Product bean = new Product();
		if (id != null) {
			bean = productService.selectById(id);
		}

		map.put("bean", bean);

		return "admin/product/toadd2";
	}

	@ModelAttribute
	public void getBean(HttpSession session, @RequestParam(value = "id", required = false) Integer id,
			Map<String, Object> map) {
		if (id != null) {
			Product bean = productService.selectById(id);
			map.put("bean", bean);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/admin/product/delete", method = RequestMethod.POST)
	public MsgHelper delete(Integer id) {
		MsgHelper msgHelper = new MsgHelper();
		productService.deleteById(id);
		return msgHelper;
	}

	/**
	 * 上传
	 * @param file
	 * @param map
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping("/admin/product/postUploadFiles")
	protected void doGet(@RequestParam(value = "data", required = false) MultipartFile file,
			@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		User user = (User) session.getAttribute("user");
		String attachPath = UPLOAD_URL;
		File file1 = new File(attachPath);
		if (!file1.exists()) {
			file1.mkdirs();
		}

		Integer index = Integer.valueOf(map.get("index").toString());
		String filename = map.get("filename").toString();
		String type = map.get("type").toString();
		String fileSavePath = file1.getPath();
		if (index == 1) {
			Integer size = Integer.valueOf(map.get("size").toString());
			String name = map.get("name").toString(); // file.getOriginalFilename()
			Integer debateId = Integer.valueOf(map.get("debateId").toString());
			System.out.println("debateId=" + debateId + ",type=" + type + ",size=" + size + ",name=" + name);

			Product product = new Product();
			product.setNumber(size);
			product.setOriginalname(name);
			product.setVideopath(filename);
			product.setCreatedate(CommonUtils.getNowDateTime());
			product.setUserid(user.getId());

			if (product.getId() == null)
				productService.insert(product);
			else {
				productService.updateById(product);
			}
		}
		upLoadFile(file.getInputStream(), fileSavePath, filename);
		response.getOutputStream().write("success".getBytes());
	}

	/**
	 * 上传
	 * 
	 * @param fileItem
	 * @param path
	 * @param noticeId
	 * @return
	 */
	public void upLoadFile(InputStream fileItem, String path, String fileName) {

		File file = new File(path, fileName);
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(fileItem);
			os = new BufferedOutputStream(new FileOutputStream(file, true));
			int len = 0;
			byte[] bs = new byte[8192];
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.flush();
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	 * 截取缩略图
	 */
	@ResponseBody
	@RequestMapping("/admin/product/videoformat")
	public Map<String, Object> videoformat(HttpServletRequest request, HttpServletResponse response, Integer debateId,
			String title, HttpSession session) throws Exception {
		Map<String, Object> message = new HashMap<>();

		String sql = "SELECT a.* FROM t_product AS a  where imagepath IS NULL OR  imagepath = ''";
		PageInfo<Product> list = commonService.getAllBySql(Product.class, sql, 0, 0);

		String ffmpegPath = session.getServletContext().getRealPath("/resources/ffmpeg/ffmpeg.exe");
		System.out.println(ffmpegPath);
		for (Product ts : list.getList()) {
			String imageName = System.currentTimeMillis() + ".jpg";
			String imagePath = UPLOAD_URL + "/" + imageName;
			String videoPath = UPLOAD_URL + "/" + ts.getVideopath();
			// ffmpeg -i 本地视频 -y -r 1 -t 1 图片名.jpg
			List<String> cmd = new ArrayList<>();
			// 视频提取工具的位置
			cmd.add(ffmpegPath);
			cmd.add("-i");
			cmd.add(videoPath);
			cmd.add("-y");
			cmd.add("-r");
			cmd.add("1");
			cmd.add("-t");
			cmd.add("1");
			cmd.add(imagePath);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			builder.redirectErrorStream(true);
			builder.start();

			ts.setImagepath(imageName);
			productService.updateById(ts);
		}
		message.put("message", "success");
		return message;
	}

	/**
	 * 取消任务
	 * @param request
	 * @param response
	 * @param productId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/admin/product/cancelvideo")
	public Map<String, Object> cancelvideo(HttpServletRequest request, HttpServletResponse response, String productId,
			HttpSession session) throws Exception {
		Map<String, Object> message = new HashMap<>();
		Map<String, Object> columnMap = new HashMap<>();
		String[] productIds = productId.split(";");
		for (String string : productIds) {
			columnMap.put("videopath", string);
		}
		productService.deleteByMap(columnMap);
		message.put("message", "success");
		return message;
	}

	/**
	 * AVI格式转化
	 * @param request
	 * @param response
	 * @param productId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/admin/product/convertvideo")
	public Map<String, Object> convertvideo(HttpServletRequest request, HttpServletResponse response, String productId,
			HttpSession session) throws Exception {
		Map<String, Object> message = new HashMap<>();
		String[] productIds = productId.split(";");
		for (String string : productIds) {
			if (string.toLowerCase().indexOf("avi") == -1) {
				continue;
			}
			String ffmpegPath = session.getServletContext().getRealPath("/resources/ffmpeg/ffmpeg.exe");
			String flvName = System.currentTimeMillis() + ".flv";
			String flvPath = UPLOAD_URL + "/" + flvName;
			String videoPath = UPLOAD_URL + "/" + string;
			if (!new File(videoPath).exists()) {
				continue;
			}
			// ffmpeg -i 本地视频 -y -c:v libx264 -strict -2 转换视频.mp4
			// ffmpeg.exe -i 1.avi -y -ab 56 -ar 22050 -b 500 -r 15 -qscale 3 f_1.flv
			List<String> cmd = new ArrayList<>();
			// 视频提取工具的位置
			cmd.add(ffmpegPath);
			cmd.add("-i");
			cmd.add(videoPath);
			cmd.add("-y");
			cmd.add("-ab");
			cmd.add("56");
			cmd.add("-ar");
			cmd.add("22050");
			cmd.add("-b");
			cmd.add("500");
			cmd.add("-r");
			cmd.add("15");
			cmd.add("-qscale");
			cmd.add("3");
			cmd.add(flvPath);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			builder.redirectErrorStream(true);
			builder.start();

			// update
			String sql = "SELECT a.* FROM t_product AS a  where videopath = '" + string + "'";
			Product one = commonService.getOne(Product.class, sql);
			if (one != null) {
				one.setVideopath(flvName);
				productService.updateById(one);
			}
		}
		message.put("message", "success");
		System.out.println("OK");
		return message;
	}
}
