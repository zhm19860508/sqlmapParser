package com.alibaba.bigData.svnloader.objects;

/**
 * Created by xiaoming.wm on 2014/8/20.
 */
public class LoaderFile {

    private Integer id;
    //文件名
    private String name;
    //版本号,git对应的objectid
    private String version;
    //相对root的路径
    private String path;
    //svn路径，git对应的是git的路径
    private String svnroot;
    //svn提交人，git对应的提交人
    private String svnCommitor;

    private String svnUpdatetime;

    private String svnComments;

    private String svnFilesize;

    //文件的url,git为对应的svnroot|path地址
    private String url;

    //分支名称
    private String branchName;

    //文件内容的字符串
    private String fileOsString;

    private String xmlType;

    private String fileMd5;

    private String fileEncoding;

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSvnroot() {
        return svnroot;
    }

    public void setSvnroot(String svnroot) {
        this.svnroot = svnroot;
    }

    public String getSvnCommitor() {
        return svnCommitor;
    }

    public void setSvnCommitor(String svnCommitor) {
        this.svnCommitor = svnCommitor;
    }

    public String getSvnUpdatetime() {
        return svnUpdatetime;
    }

    public void setSvnUpdatetime(String svnUpdatetime) {
        this.svnUpdatetime = svnUpdatetime;
    }

    public String getSvnComments() {
        return svnComments;
    }

    public void setSvnComments(String svnComments) {
        this.svnComments = svnComments;
    }

    public String getSvnFilesize() {
        return svnFilesize;
    }

    public void setSvnFilesize(String svnFilesize) {
        this.svnFilesize = svnFilesize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getFileOsString() {
        return fileOsString;
    }

    public void setFileOsString(String fileOsString) {
        this.fileOsString = fileOsString;
    }

    public String getXmlType() {
        return xmlType;
    }

    public void setXmlType(String xmlType) {
        this.xmlType = xmlType;
    }
}
