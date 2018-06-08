package com.brh.channel.common.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

public class FastDfsClient {

	private static Logger logger = Logger.getLogger(FastDfsClient.class);
	// 默认group1是使用token提供http服务的数据组
	public static final String DEFAULT_SECURED_HTTP_GROUP_NM = "group1";
	// 默认public库
	public static final String PUBLIC_GROUP_NM = "publicgroup";

	// 指定配置文件路径，支持从classpath读取;
	private static final String FAST_DFS_CONF_FILE = "fdfs_client.conf";

	static {
		try {
			// 初始化配置文件
			ClientGlobal.init(FAST_DFS_CONF_FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取存储服务器连接
	 * 
	 * @return
	 * @throws IOException
	 */
	private static StorageClient getStorageClient() throws IOException {

		// 建立tracker server 的连接
		/*
		 * TrackerGroup tg = new TrackerGroup( new InetSocketAddress[] { new
		 * InetSocketAddress( TRACKER_SERVER_IP, TRACKER_SERVER_PORT) });
		 */

		// TrackerClient tc = new TrackerClient();
		TrackerServer ts = ClientGlobal.getG_tracker_group().getConnection();
		if (ts == null) {
			System.out.println("getConnection return null");
			return null;
		}

		// 建立存储服务器的连接
		StorageServer ss = null;// tc.getStoreStorage(ts);
		/*
		 * if (ss == null) { System.out.println("getStoreStorage return null");
		 * return null; }
		 */

		// 建立存储客户端
		StorageClient sc = new StorageClient(ts, ss);

		/* for test only */
		logger.info("active test to storage server: "
				+ ProtoCommon.activeTest(ss.getSocket()));
		ss.close();
		/* for test only */
		logger.info("active test to tracker server: "
				+ ProtoCommon.activeTest(ts.getSocket()));
		ts.close();

		return sc;
	}

	/**
	 * 返回使用combined命名方式的StorageClient供前端使用
	 * 
	 * @return
	 * @throws IOException
	 */
	private static StorageClient1 getStorageClient1() throws IOException {

		// 建立tracker server 的连接
		/*
		 * TrackerGroup tg = new TrackerGroup( new InetSocketAddress[] { new
		 * InetSocketAddress( TRACKER_SERVER_IP, TRACKER_SERVER_PORT) });
		 */

		// TrackerClient tc = new TrackerClient();
		TrackerServer ts = ClientGlobal.getG_tracker_group().getConnection();
		if (ts == null) {
			System.out.println("getConnection return null");
			return null;
		}

		// 建立存储服务器的连接
		StorageServer ss = null;// tc.getStoreStorage(ts);
		/*
		 * if (ss == null) { System.out.println("getStoreStorage return null");
		 * return null; }
		 */

		// 建立存储客户端
		StorageClient1 sc = new StorageClient1(ts, ss);

		/* for test only */
		logger.info("active test to storage server: "
				+ ProtoCommon.activeTest(ss.getSocket()));
		ss.close();
		/* for test only */
		logger.info("active test to tracker server: "
				+ ProtoCommon.activeTest(ts.getSocket()));
		ts.close();

		return sc;
	}

	/**
	 * 上传文件 每次重新连接trackeServer,防止节点失效
	 * 
	 * @param fileName
	 *            原始文件名称，放置到文件属性中
	 * @param fileExt
	 *            文件扩展名，直接添加到fastDfs生成id最后
	 * @param contents
	 *            文件内容
	 *            <ul>
	 *            <li>results[0]: the group name to store the file</li>
	 *            </ul>
	 *            <ul>
	 *            <li>results[1]: the new created filename</li>
	 *            </ul>
	 * @return
	 */
	public static String[] uploadFile(String fileName, String fileExt,
			byte[] contents) {
		TrackerServer trackerServer=null;
		try {
			if (fileName == null || contents == null) {
				logger.error("Upload file[" + fileName + "] is null");
				return null;
			}

			long startTime = System.currentTimeMillis();
			logger.info("Upload file[" + fileName + "] startTime::" + startTime);

			// TrackerClient tracker = new TrackerClient();、
			InetSocketAddress[] address = ClientGlobal.g_tracker_group.tracker_servers;
//			logger.info("connectTimeOut:"+ClientGlobal.getG_connect_timeout());
//			logger.info("netWorkTimeOut:"+ClientGlobal.getG_network_timeout());
			for (InetSocketAddress temp:address) {
				logger.info("=============================:" + temp);
			}
			trackerServer = ClientGlobal.getG_tracker_group()
					.getConnection();
			StorageServer storageServer = null;
			StorageClient client = new StorageClient(trackerServer,
					storageServer);

			NameValuePair[] meta_list = new NameValuePair[1];
			meta_list[0] = new NameValuePair("filename", fileName);
			String[] fileInfo = client
					.upload_file(contents, fileExt, meta_list);
		
			// example:M00/00/00/eWXds1DJpzCASX0oAAAAA4i3nNI382.txt
			logger.info("Upload file[" + fileName + "] ok " + "file group["
					+ fileInfo[0] + "] " + "file id[" + fileInfo[1] + "]");

			long endTime = System.currentTimeMillis();
			logger.info("Upload file[" + fileName + "] endTime::" + endTime);

			return fileInfo;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}finally{
			//获取到StorageServer后关闭trackerServer 
			try {
				if(trackerServer!=null)
				trackerServer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param groupName
	 *            组名
	 * @param fileName
	 *            文件名
	 * @return 0 成功，非0失败
	 */
	public static Integer deleteFile(String groupName, String fileName) {
		try {
			long startTime = System.currentTimeMillis();
			logger.info("Delete file[" + fileName + "] startTime::" + startTime);

			// 建立存储客户端
			// StorageClient sc = getStorageClient();

			// 建立tracker server 的连接
			// TrackerClient tc = new TrackerClient();
			TrackerServer ts = ClientGlobal.getG_tracker_group()
					.getConnection();
			if (ts == null) {
				logger.error("getConnection return null");
				return null;
			}

			// 建立存储服务器的连接
			StorageServer ss = null;// tc.getStoreStorage(ts);

			// 建立存储客户端
			StorageClient sc = new StorageClient(ts, ss);

			Integer x = sc.delete_file(groupName, fileName);

			logger.info("Delete file[" + fileName + "] ok");

			long endTime = System.currentTimeMillis();
			logger.info("Delete file[" + fileName + "] endTime::" + endTime);

			return x;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 下载文件
	 * 
	 * @param groupName
	 * @param fileName
	 * @return
	 */
	public static byte[] downloadFile(String groupName, String fileName) {
		try {
			long startTime = System.currentTimeMillis();
			logger.info("Download file[" + fileName + "] startTime::"
					+ startTime);

			// 建立存储客户端
			// StorageClient sc = getStorageClient();

			// 建立tracker server 的连接
			// TrackerClient tc = new TrackerClient();
			TrackerServer ts = ClientGlobal.getG_tracker_group()
					.getConnection();
			if (ts == null) {
				logger.error("getConnection return null");
				return null;
			}

			// 建立存储服务器的连接
			StorageServer ss = null;// tc.getStoreStorage(ts);

			// 建立存储客户端
			StorageClient sc = new StorageClient(ts, ss);

			byte[] localfileByteArr = sc.download_file(groupName, fileName);

			logger.info("Download file[" + fileName + "] ok");

			long endTime = System.currentTimeMillis();
			logger.info("Download file[" + fileName + "] endTime::" + endTime);

			return localfileByteArr;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成token
	 * 
	 * @param remoteFileNm
	 *            fastDFS存储文件名
	 *            example:M00/00/00/CtMBoVc5M3mAUEG0AAAADv4ZzcQ409-part1.txt
	 * @return url后缀
	 *         example:?token=346335569a4a85b2bcf9f707719dbf23&ts=1463385980
	 */
	public static String generateAccessToken(String remoteFileNm) {

		if (remoteFileNm == null || remoteFileNm.equals("")) {
			logger.error("generate token failed:input file id is null");
			return null;
		}
		int ts = (int) (System.currentTimeMillis() / 1000);
		ts = 1467010299;
		String token;
		try {
			token = ProtoCommon.getToken(remoteFileNm, ts,
					ClientGlobal.getG_secret_key());
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException
				| MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return "?token=" + token + "&ts=" + ts;
	}

	/**
	 * 获取文件属性
	 * 
	 * @param fileId
	 * @return
	 */
	public static NameValuePair[] getMetaInfoById(String groupId, String fileId) {
		try {
			TrackerServer ts = ClientGlobal.getG_tracker_group()
					.getConnection();
			if (ts == null) {
				logger.error("getConnection return null");
				return null;
			}

			// 建立存储服务器的连接
			StorageServer ss = null;// tc.getStoreStorage(ts);

			// 建立存储客户端
			StorageClient sc = new StorageClient(ts, ss);

			NameValuePair[] meta_list = sc.get_metadata(groupId, fileId);
			return meta_list;
		} catch (MyException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		
//		String aaa = generateAccessToken("/M00/00/46/CtMBoVdwzOiAKTFaAAI_vUQXWVo132.jpg");
//		System.out.println(aaa);
//		// 测试上传
//		String[] uploadResult = FastDfsClient.uploadFile("testfilename",
//				"jpg", FileUtil.getBytes("e:\\DSC06741.JPG"));
//		if (uploadResult != null) {
//			System.out.println(uploadResult[0]);
//			System.out.println(uploadResult[1]);
//		}
//		FileOutputStream fos = null;
//		try {
//			// 测试下载
//			byte[] downloadBytes = FastDFSClient.downloadFile(
//					FastDFSClient.DEFAULT_SECURED_HTTP_GROUP_NM,
//					uploadResult[1]);
//			fos = new FileOutputStream(new File(
//					"E:\\download.jpg"));
//			fos.write(downloadBytes);
//			fos.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (fos!=null) {
//				try {
//					fos.close();
//				} catch (Exception e) {
//					
//				}
//			}
//		}
////		for (int i=0;i<50;i++) {
////			Thread tmp = new Thread(new ThreadFileHandler());
////			tmp.start();
////			try {
////				Thread.sleep(10);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
	}

}
