package com.brh.channel.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

public class FtpUtil {
	/**
	 * 
	 * FTP客户端
	 */
	public static FtpClient ftpClient;
	private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	/**
	 * 服务器连接
	 * 
	 * @param ip
	 *            服务器IP
	 * @param port
	 *            服务器端口
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            服务器路径
	 * @throws FtpProtocolException
	 * 
	 */
	@SuppressWarnings("restriction")
	public static void connectServer(String ip, int port, String user,
			String password, String path) throws FtpProtocolException {
		try {
			/* ******连接服务器****** */
			ftpClient = FtpClient.create();
			ftpClient.connect(new InetSocketAddress(ip, port));
			ftpClient.enablePassiveMode(true);// 被动模式
			ftpClient.login(user, password.toCharArray());
			// 设置成2进制传输
			ftpClient.setBinaryType();
			System.out.println("login success!");

			if (path.length() != 0) {
				// 把远程系统上的目录切换到参数path所指定的目录
				ftpClient.changeDirectory(path);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	}

	/**
	 * 关闭连接
	 * 
	 */

	@SuppressWarnings("restriction")
	public static void closeConnect() {
		try {
			ftpClient.close();
			System.out.println("disconnect success");
		} catch (IOException ex) {
			System.out.println("not disconnect");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 
	 * 上传文件
	 * 
	 * @param localFile
	 *            本地文件
	 * @param remoteFile
	 *            远程文件
	 * @throws FtpProtocolException
	 */
	@SuppressWarnings("restriction")
	public static void upload(String localFile, String remoteFile)
			throws FtpProtocolException {
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			// 将远程文件加入输出流中
			os = (TelnetOutputStream) ftpClient.putFileStream(remoteFile, true);

			// 获取本地文件的输入流
			File file_in = new File(localFile);
			is = new FileInputStream(file_in);

			// 创建一个缓冲区
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			logger.info("read success");
		} catch (IOException ex) {
			logger.info("not upload");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ByteArrayOutputStream output 下载文件
	 * 
	 * @param remoteFile
	 *            远程文件路径(服务器端)
	 * @param localFile
	 *            本地文件路径(客户端)
	 * @throws FtpProtocolException
	 * 
	 */
	@SuppressWarnings("restriction")
	public static void download(String remoteFile, String localFile)
			throws FtpProtocolException {
		TelnetInputStream is = null;
		FileOutputStream os = null;
		try {

			// 获取远程机器上的文件filename，借助TelnetInputStream把该文件传送到本地。
			is = (TelnetInputStream) ftpClient.getFileStream(remoteFile);
			File file_in = new File(localFile);
			os = new FileOutputStream(file_in);

			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			System.out.println("download success");
		} catch (IOException ex) {
			System.out.println("not download");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 下载文件
	 * 
	 * @param remoteFile
	 *            远程文件路径(服务器端)
	 * @param localFile
	 *            本地文件路径(客户端)
	 * @throws FtpProtocolException
	 * @throws IOException 
	 * 
	 */
	@SuppressWarnings("restriction")
	public static void downloadToStream(String remoteFile,
			ByteArrayOutputStream output,InputStream is) throws FtpProtocolException, IOException {
		
			// 获取远程机器上的文件filename，借助InputStream把该文件传送到本地。
		    is = (InputStream) ftpClient.getFileStream(remoteFile);
			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = is.read(buffer))) {
				output.write(buffer, 0, n);
			}
			logger.info("read success");
				
	}

}