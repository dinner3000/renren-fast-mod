package com.brh.channel.common.utils;

import java.util.List;

/**
 * @author wangsanhu
 * @create 2017-09-22
 */
public class FileTreeNode {
    public static final String TYPE_DIR = "DIR";

    public static final String TYPE_FILE = "FILE";

    public FileTreeNode(String name,String type,String filePath){
        this.name = name;
        this.type = type;
        this.filePath = filePath;
        children = null;
    }

    public FileTreeNode(){};

    public FileTreeNode(String name,String type,String filePath,List<FileTreeNode> children){
        this.name = name;
        this.type = type;
        this.filePath = filePath;
        this.children = children;
    }
    private String name;

    private String type;

    private String filePath;

    private List<FileTreeNode> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<FileTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<FileTreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "FileTreeNode{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", filePath='" + filePath + '\'' +
                ", children=" + children +
                '}';
    }
}
