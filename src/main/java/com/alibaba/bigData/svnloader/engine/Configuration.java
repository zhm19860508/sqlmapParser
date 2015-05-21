package com.alibaba.bigData.svnloader.engine;

import java.util.List;

/**
 * Created by xiaoming.wm on 2014/8/20.
 * 传入配置
 */
public class Configuration {

    private String pathType;

    private String gitUserName;
    private String gitPassWord;
    private String pathFilter;
    private String suffixFilter;

    private List<String> writeSvnPathList;
    private List<String> blackSvnPathList;
    private String svnUserName;
    private String svnPassWord;

    public String getPathType() {
        return pathType;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
    }

    public String getGitUserName() {
        return gitUserName;
    }

    public void setGitUserName(String gitUserName) {
        this.gitUserName = gitUserName;
    }

    public String getGitPassWord() {
        return gitPassWord;
    }

    public void setGitPassWord(String gitPassWord) {
        this.gitPassWord = gitPassWord;
    }

    public String getPathFilter() {
        return pathFilter;
    }

    public void setPathFilter(String pathFilter) {
        this.pathFilter = pathFilter;
    }

    public String getSuffixFilter() {
        return suffixFilter;
    }

    public void setSuffixFilter(String suffixFilter) {
        this.suffixFilter = suffixFilter;
    }

    public List<String> getWriteSvnPathList() {
        return writeSvnPathList;
    }

    public void setWriteSvnPathList(List<String> writeSvnPathList) {
        this.writeSvnPathList = writeSvnPathList;
    }

    public List<String> getBlackSvnPathList() {
        return blackSvnPathList;
    }

    public void setBlackSvnPathList(List<String> blackSvnPathList) {
        this.blackSvnPathList = blackSvnPathList;
    }

    public String getSvnUserName() {
        return svnUserName;
    }

    public void setSvnUserName(String svnUserName) {
        this.svnUserName = svnUserName;
    }

    public String getSvnPassWord() {
        return svnPassWord;
    }

    public void setSvnPassWord(String svnPassWord) {
        this.svnPassWord = svnPassWord;
    }
}
