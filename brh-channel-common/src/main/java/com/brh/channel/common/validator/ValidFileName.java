package com.brh.channel.common.validator;

import org.apache.log4j.Logger;

public class ValidFileName {
	private static Logger LOG=Logger.getLogger(ValidFileName.class);
	//校验文件名含非法字符
	public static boolean isValidFileName(String fileName) { 
		if (fileName == null || fileName.length() > 255) {
			return false; 
		}else{ 
			return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$"); 
		}
	
	}
	//校验文件名后缀
	public static boolean isValidFileType(String fileName) { 
		boolean flag = false;
		LOG.info(fileName);
		if (fileName == null || fileName.length() > 255) {
			return flag; 
		}
		
		fileName = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length()).toLowerCase();
		String[] fileTypeArray = {"jpeg","bmp","png","JPG","jpg","docx","doc","ppt","pptx","pps","xls","xlsx","pdf","txt"};
	
		LOG.debug(fileName);
		for(String fileType : fileTypeArray){
			LOG.debug(fileType);
			if(fileName.equalsIgnoreCase(fileType)){
				flag = true;
				break;
			}else{
				continue;
			}
		}
		
		return flag;
	}
	
}