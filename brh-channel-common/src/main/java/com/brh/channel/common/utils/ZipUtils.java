package com.brh.channel.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangsanhu
 * @create 2017-09-22
 */
public class ZipUtils {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 创建ZIP文件
     * @param sourcePath 文件或文件夹路径
     * @param zipPath 生成的zip文件存在路径（包括文件名）
     */
    public static void createZip(String sourcePath, String zipPath) throws IOException {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {

            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZip(new File(sourcePath), "", zos);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                logger.error("创建ZIP文件失败",e);
            }

        }
    }

    public static void createZipFromDFS(FileTreeNode rootNode, String zipPath) throws IOException {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {

            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZipDFS(rootNode, "", zos);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                logger.error("创建ZIP文件失败",e);
            }

        }


    }

    public static void createZipFromDFS(FileTreeNode rootNode, OutputStream fos) throws IOException {
        ZipOutputStream zos = null;


        zos = new ZipOutputStream(fos);
        writeZipDFS(rootNode, "", zos);
        /*finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                logger.error("创建ZIP文件失败",e);
            }

        }*/
    }


    private static void writeZipDFS(FileTreeNode node, String parentPath, ZipOutputStream zos) throws IOException {

        if(node!=null){
            if(node.getType().equals(FileTreeNode.TYPE_DIR)){//处理文件夹
                parentPath+=node.getName()+File.separator;
                List<FileTreeNode> nodeList=node.getChildren();
                if(nodeList==null||nodeList.size()==0){
                    zos.putNextEntry(new ZipEntry(parentPath));
                }else {
                    for (FileTreeNode childNode : nodeList) {
                        writeZipDFS(childNode, parentPath, zos);
                    }
                }
            }else{
                String[] group = node.getFilePath().split(",");

                byte[] downloadBytes = FastDfsClient.downloadFile(group[0],
                        group[1]);

                ZipEntry ze = new ZipEntry(parentPath + node.getName());
                zos.putNextEntry(ze);
                zos.write(downloadBytes);
                zos.flush();
            }
        }
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) throws IOException {
        if(file.exists()){
            if(file.isDirectory()){//处理文件夹
                parentPath+=file.getName()+File.separator;
                File [] files=file.listFiles();
                for(File f:files){
                    writeZip(f, parentPath, zos);
                }
            }else{
                FileInputStream fis=null;
                DataInputStream dis=null;
                try {
                    fis=new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                        zos.flush();
                    }

                }finally{
                    try {
                        if(dis!=null){
                            dis.close();
                        }
                    }catch(IOException e){
                        logger.error("创建ZIP文件失败",e);
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        try {
            FileTreeNode r = new FileTreeNode("root",FileTreeNode.TYPE_DIR,null,new ArrayList<FileTreeNode>());
            r.getChildren().add(new FileTreeNode("abc",FileTreeNode.TYPE_DIR,null,null));
            r.getChildren().add(new FileTreeNode("ajpg", FileTreeNode.TYPE_DIR, null, null));

            String zipPath = "/Users/wangsanhu/projects/brh/brh-channel/trunk/code/brh-channel/brh-channel-web/src/main/webapp/templet/ziptemplate/1.zip";
            createZipFromDFS(r,zipPath);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
