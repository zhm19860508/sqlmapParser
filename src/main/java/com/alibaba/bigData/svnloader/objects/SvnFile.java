package com.alibaba.bigData.svnloader.objects;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-1-13
 * Time: 下午8:58
 * svnFile的结构体
 */
public class SvnFile {

    private String fileName;
    private String filePath;
    private String fileUrl;
    private String Type;
    private String author;
    private long revision;
    private String changeDate;
    private String logMessage;
    private String repositoryRoot;
    private long fileSize;
    private String fileOsString;
    private String fileEncoiding;
    private String fileMd5;
    private String fileRelPath;
    private String versionNum;

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getFileRelPath() {
        return fileRelPath;
    }

    public void setFileRelPath(String fileRelPath) {
        this.fileRelPath = fileRelPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getRevision() {
        return revision;
    }

    public void setRevision(long revision) {
        this.revision = revision;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }


    public String getRepositoryRoot() {
        return repositoryRoot;
    }

    public void setRepositoryRoot(String repositoryRoot) {
        this.repositoryRoot = repositoryRoot;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileOsString() {
        return fileOsString;
    }

    public void setFileOsString(String fileOsString) {
        this.fileOsString = fileOsString;
    }

    public String getFileEncoiding() {
        return fileEncoiding;
    }

    public void setFileEncoiding(String fileEncoiding) {
        this.fileEncoiding = fileEncoiding;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
}
