package com.brh.channel.common.utils;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("excelFileUtil")
@Scope("prototype")
public class ExcelFileUtil {
	private static Logger log = Logger.getLogger(ExcelFileUtil.class);

	/**
	 * 创建excel文件
	 * @param templateName  电子表格模板名称
	 * @param dataList  数据源
	 * @param excelTitle  电子表格抬头
	 * @param response
	 * @throws Exception 
	 * @throws Exception
	 */
	public static String createExcel(String templateName, List<?> dataList, String excelTitle,
			HttpServletRequest request, HttpServletResponse response) {
		// 随机生成一个不重复的字符串
		String name = UUID.randomUUID().toString();

		// 生成相应的execl文件
		String templateFileName = "/templet/execltemplate/template_" + templateName + ".xls";
		String resultFileName = "/templet/execltemplate/" + name + "_out.xls";

		// 生成title
		List<String> list_title = new ArrayList<String>();
		// title格式：2014-08-05 18:22:05_excelTitle
		Calendar calendar = new GregorianCalendar();
		String title = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "  " + calendar.get(Calendar.HOUR_OF_DAY)
				+ ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "_"
				+ excelTitle;
		list_title.add(title);
		createExcelFile(templateFileName, dataList, list_title, resultFileName, request);
		// String path = System.getProperty("user.dir") +
		// "/src/main/webapp/commons/execltemplate/" + name + "_out.xls";
		// user.dir导致的FileNotFound
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/templet/execltemplate/" + name + "_out.xls";
		// path是指欲下载的文件的路径。
		return path;
	}

	/**
	 * 生成excel模板
	 * @param colCnm  字段中文名
	 * @param colNm	  字段对应属性名
	 * @author 冯智文
	 */
	public static void createTemplate(String filepath, String[] colCnm, String[] colNm) {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filepath + "temp.xls");
			// 生成Excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			sheet.setDefaultColumnWidth(15);
			// 标题
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			HSSFRichTextString text = new HSSFRichTextString("${title}");
			cell.setCellValue(text);
			// 列名
			row = sheet.createRow(1);
			for (int i = 0; i < colCnm.length; i++) {
				cell = row.createCell(i);
				text = new HSSFRichTextString(colCnm[i].trim());
				cell.setCellValue(text);
			}
			// 遍历集合名
			row = sheet.createRow(2);
			cell = row.createCell(0);
			text = new HSSFRichTextString("<jx:forEach items=\"${list}\" var=\"domain\">");
			cell.setCellValue(text);
			// 需要遍历的属性
			row = sheet.createRow(3);
			for (int i = 0; i < colNm.length; i++) {
				cell = row.createCell(i);
				text = new HSSFRichTextString("${domain." + colNm[i].trim() + "}");
				cell.setCellValue(text);
			}
			// 遍历结束
			row = sheet.createRow(4);
			cell = row.createCell(0);
			text = new HSSFRichTextString("</jx:forEach>");
			cell.setCellValue(text);

			workbook.write(fileOut);
			fileOut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	
	/**
	 * 下载创建的excel文件
	 * @param path  下载的文件的路径
	 * @param excelTitle  电子表格抬头
	 * @param response
	 * @throws Exception 
	 * @throws Exception
	 */
	public static void downloadExcel(String path, String excelTitle, HttpServletRequest request,
			HttpServletResponse response) {
		// path是指欲下载的文件的路径。
		File file = new File(path);
		// 取得文件名。
		String filename = file.getName();
		// 更改文件名
		Calendar calendar2 = new GregorianCalendar();
//		filename = excelTitle + "_" + calendar2.get(Calendar.YEAR) + "-"
//				+ (calendar2.get(Calendar.MONTH) + 1) + "-" + calendar2.get(Calendar.DAY_OF_MONTH)
//				+ ".xls";
		filename = excelTitle + "_" + DateUtils.getCurrentFullTime()
				+ ".xls";
		// 以流的形式下载文件。
		BufferedInputStream fis = null;
		OutputStream toClient = null;
		try {
			filename = new String(filename.getBytes("gb2312"), "ISO8859-1");
			fis = new BufferedInputStream(new FileInputStream(path));
//			byte[] buffer = new byte[fis.available()];
//			fis.read(buffer);
//			fis.close();
			response.reset();// 清空response
			// 设置response的Header
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			byte [] content=new byte[1024*1024];
			int len;
			while((len=fis.read(content))!=-1){
				toClient.write(content,0,len);
				toClient.flush();
			}
//			toClient.write(buffer);
//			toClient.flush();
//			toClient.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("excel export exception :" + e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (toClient != null) {
					toClient.close();
				}
			} catch (IOException e) {
				log.debug("stream closed exception " + e);
			}

		}
		// 将相应的文件删除
		File excelFile = new File(path);
		if (excelFile.exists()) {
			excelFile.delete();
		}
	}

	/**
	 * 内部方法
	 */
	public static void createExcelFile(String templateFileName, List<?> list, List<?> title,
			String resultFileName, HttpServletRequest request) {
		// 创建XLSTransformer对象
		XLSTransformer transformer = new XLSTransformer();
		// 获取java项目编译后根路径
		// URL url = this.getClass().getClassLoader().getResource("");

		// String path = System.getProperty("user.dir");
		String path = request.getSession().getServletContext().getRealPath("/"); // user.dir导致的FileNotFound
		// 得到模板文件路径
		// String srcFilePath = url.getPath() + templateFileName;
		String srcFilePath = path + templateFileName;
		Map<String, Object> beanParams = new HashMap<String, Object>();
		beanParams.put("list", list);
		beanParams.put("title", title);
		// String destFilePath = url.getPath() + resultFileName;
		String destFilePath = path + resultFileName;
		log.debug("==============srcFilePath==" + srcFilePath);
		log.debug("==============destFilePath==" + destFilePath);
		try {
			// 生成Excel文件
			transformer.transformXLS(srcFilePath, beanParams, destFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("create excel exception" + e);
		}
	}

	/**
	 * 创建用户自定义excel文件
	 * @param templateName  电子表格模板名称
	 * @param dataList  数据源
	 * @param excelTitle  电子表格抬头
	 * @param response
	 * @throws Exception 
	 * @throws Exception
	 */
	public static String createUserDefinedExcel(String templateName, List<?> dataList, String excelTitle,
			HttpServletRequest request, HttpServletResponse response, Map userArgs) {
		// 随机生成一个不重复的字符串
		String name = UUID.randomUUID().toString();

		// 生成相应的execl文件
		String templateFileName = "/commons/execltemplate/reportTemplate_" + templateName + ".xls";
		String resultFileName = "/commons/execltemplate/" + name + "_out.xls";

		// 生成title
		List<String> list_title = new ArrayList<String>();
		// title格式：2014-08-05 18:22:05_excelTitle
		Calendar calendar = new GregorianCalendar();
		String title = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "  " + calendar.get(Calendar.HOUR_OF_DAY)
				+ ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "_"
				+ excelTitle;
		list_title.add(title);
		createUserDefinedExcelFile(templateFileName, dataList, list_title, resultFileName, request,
				userArgs);
		// String path = System.getProperty("user.dir") +
		// "/src/main/webapp/commons/execltemplate/" + name + "_out.xls";
		// user.dir导致的FileNotFound
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/commons/execltemplate/" + name + "_out.xls";
		// path是指欲下载的文件的路径。
		return path;
	}

	/**
	 * 
	 * @param path  下载的文件的路径
	 * @param excelTitle  电子表格抬头
	 * @param request
	 * @param response
	 */
	public static void downloadUserDefinedExcel(String path, String excelTitle,
			HttpServletRequest request, HttpServletResponse response) {
		File file = new File(path);
		// 取得文件名。
		String filename = file.getName();
		// 更改文件名
		Calendar calendar2 = new GregorianCalendar();
		filename = excelTitle + "_" + calendar2.get(Calendar.YEAR) + "-"
				+ (calendar2.get(Calendar.MONTH) + 1) + "-" + calendar2.get(Calendar.DAY_OF_MONTH)
				+ ".xls";
		// 以流的形式下载文件。
		BufferedInputStream fis = null;
		OutputStream toClient = null;
		try {
			filename = new String(filename.getBytes("gb2312"), "ISO8859-1");
			fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();// 清空response
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/vnd.ms-excel");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			log.debug("excel export exception :" + e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (toClient != null) {
					toClient.close();
				}
			} catch (IOException e) {
				log.debug("stream closed exception " + e);
			}

		}
		// 将相应的文件删除
		File excelFile = new File(path);
		if (excelFile.exists()) {
			excelFile.delete();
		}
	}

	/**
	 * 内部方法
	 */
	public static void createUserDefinedExcelFile(String templateFileName, List<?> list, List<?> title,
			String resultFileName, HttpServletRequest request, Map userArgs) {
		// 创建XLSTransformer对象
		XLSTransformer transformer = new XLSTransformer();
		// 获取java项目编译后根路径
		// URL url = this.getClass().getClassLoader().getResource("");

		// String path = System.getProperty("user.dir");
		String path = request.getSession().getServletContext().getRealPath("/"); // user.dir导致的FileNotFound
		// 得到模板文件路径
		// String srcFilePath = url.getPath() + templateFileName;
		String srcFilePath = path + templateFileName;
		Map<String, Object> beanParams = new HashMap<String, Object>();
		beanParams.put("list", list);
		beanParams.put("title", title);
		// 向beanParams加入用户的参数userArgs
		Set keyset = userArgs.keySet();
		Iterator itr = keyset.iterator();
		while (itr.hasNext()) {
			String keyName = itr.next().toString();
			String keyValue = (String) userArgs.get(keyName);
			beanParams.put(keyName, keyValue);// 添加到beanParams
		}
		// String destFilePath = url.getPath() + resultFileName;
		String destFilePath = path + resultFileName;
		log.debug("==============srcFilePath==" + srcFilePath);
		log.debug("==============destFilePath==" + destFilePath);
		try {
			// 生成Excel文件
			transformer.transformXLS(srcFilePath, beanParams, destFilePath);
		} catch (Exception e) {
			log.debug("create excel exception" + e);
		}
	}

	public static String createExcelNoTemp(String[] colCnm, String[] colNm, List<?> dataList, String excelTitle,
			HttpServletRequest request, HttpServletResponse response) {
		log.debug("==============exportExcel==" + excelTitle);
		log.debug("==============dataList==" + dataList.size());
		// 随机生成一个不重复的字符串
		String name = UUID.randomUUID().toString();
		// 生成相应的execl文件
		String srcFilePath =  request.getSession().getServletContext().getRealPath("/")
				+ "/commons/execltemplate/";
		String resultFileName =  request.getSession().getServletContext().getRealPath("/")
				+ "/commons/execltemplate/" + name+"_out.xls";

		// 生成title
		List<String> list_title = new ArrayList<String>();

		Calendar calendar = new GregorianCalendar();
		String title = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "  " + calendar.get(Calendar.HOUR_OF_DAY)
				+ ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "_"
				+ excelTitle;
		list_title.add(title);

		// 生成模板
		createTemplate(srcFilePath, colCnm, colNm);
		
		XLSTransformer transformer = new XLSTransformer();
		Map<String, Object> beanParams = new HashMap<String, Object>();
		beanParams.put("list", dataList);
		beanParams.put("title", list_title);
		log.debug("==============srcFilePath==" + srcFilePath);
		log.debug("==============resultFileName==" + resultFileName);
		try {
			// 使用模板和数据生成临时Excel文件
			transformer.transformXLS(srcFilePath + "temp.xls", beanParams, resultFileName);
		} catch (Exception e) {
			log.debug("create excel exception" + e);
		}
		// path是指欲下载的文件的路径。
		return resultFileName;
	}
}