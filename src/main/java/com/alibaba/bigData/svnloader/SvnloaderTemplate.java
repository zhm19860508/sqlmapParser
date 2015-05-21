package com.alibaba.bigData.svnloader;

import com.alibaba.bigData.svnloader.engine.Configuration;
import com.alibaba.bigData.svnloader.engine.GitFileloader;
import com.alibaba.bigData.svnloader.engine.SvnFileLoader;
import com.alibaba.bigData.svnloader.objects.GitFile;
import com.alibaba.bigData.svnloader.objects.LoaderFile;
import com.alibaba.bigData.svnloader.objects.SvnFile;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoming.wm on 2014/8/20.
 * SVNLAODER对外调用类111
 */
public class SvnloaderTemplate {


    public static final String LOADER_TYPE_GIT = "git";
    public static final String LOADER_TYPE_SVN = "svn";
    public static final String NO_SUPPORT_TYPE = "非SVN和GIT不支持的类型";

    private Configuration configuration;
    private String url = "";

    /**
     * 初始化方法
     */
    public void init(Configuration configuration, String url) {
        this.configuration = configuration;
        this.url = url;
    }

    /**
     * 获取文件方法
     */
    public List<LoaderFile> run() throws Exception {

        List<LoaderFile> fileDOList = new ArrayList<LoaderFile>();
        if (configuration.getPathType().equalsIgnoreCase(LOADER_TYPE_GIT)) {
            configuration.setPathType(LOADER_TYPE_GIT);
            GitFileloader gitloader = new GitFileloader(configuration);
            String branch=getGitBranchNameByPath(url);
            String path=StringUtils.trim(url).substring(0,url.indexOf(".git")+4);
            gitloader.init(path, branch);
            List<GitFile> gitFileList = gitloader.getGitFiles();
            for (GitFile gitFile : gitFileList) {
                LoaderFile fileDO = new LoaderFile();
                fileDO.setName(gitFile.getFileName());
                fileDO.setVersion(String.valueOf(gitFile.getRevision()));
                fileDO.setPath(gitFile.getFilePath());
                fileDO.setSvnroot(gitFile.getRepositoryRoot());
                fileDO.setSvnCommitor(gitFile.getAuthor());
                fileDO.setSvnUpdatetime(gitFile.getChangeDate());
                fileDO.setSvnComments(gitFile.getLogMessage());
                fileDO.setSvnFilesize(String.valueOf(gitFile.getFileSize()));
                fileDO.setFileMd5(gitFile.getFileMd5());
                fileDO.setFileEncoding(gitFile.getFileEncoiding());
                fileDO.setUrl(gitFile.getRepositoryRoot() + "|" + branch + "|" + gitFile.getFilePath());
                fileDO.setBranchName(branch);
                fileDO.setFileOsString(gitFile.getFileOsString());
                fileDOList.add(fileDO);
            }
        } else if (configuration.getPathType().equalsIgnoreCase(LOADER_TYPE_SVN)) {
            SvnFileLoader svnloader = new SvnFileLoader(configuration);
            String path=StringUtils.trim(url);
            svnloader.init(path);
            String branch=getSvnBranchNameByPath(path);
            List<SvnFile> svnFileDOList = svnloader.getSvnlogFiles();
            for (SvnFile svnFileDO : svnFileDOList) {
                LoaderFile fileDO = new LoaderFile();
                fileDO.setName(svnFileDO.getFileName());
                fileDO.setVersion(String.valueOf(svnFileDO.getRevision()));
                fileDO.setPath(svnFileDO.getFilePath());
                fileDO.setSvnroot(svnFileDO.getRepositoryRoot());
                fileDO.setSvnCommitor(svnFileDO.getAuthor());
                fileDO.setSvnUpdatetime(svnFileDO.getChangeDate());
                fileDO.setSvnComments(svnFileDO.getLogMessage());
                fileDO.setSvnFilesize(String.valueOf(svnFileDO.getFileSize()));
                fileDO.setFileMd5(svnFileDO.getFileMd5());
                fileDO.setFileEncoding(svnFileDO.getFileEncoiding());
                fileDO.setUrl(svnFileDO.getFileUrl());
                fileDO.setBranchName(branch);
                fileDO.setFileOsString(svnFileDO.getFileOsString());
                fileDOList.add(fileDO);
            }
        } else {
            throw new Exception(NO_SUPPORT_TYPE);
        }
        return fileDOList;
    }


    private String getSvnBranchNameByPath(String path) {

        if (path.indexOf(";") > 0) {
            path = StringUtils.deleteWhitespace(path.split(";")[0]);
        }
        if (path.indexOf("/branches") >= 0) {
            int start = path.indexOf("/branches/");
            int startpoint = path.indexOf("/", start + 1);
            if (path.indexOf("/", startpoint + 1) > -1) {
                int endpoint = path.indexOf("/", startpoint + 1);
                return path.substring(startpoint + 1, endpoint);
            } else {
                return path.substring(startpoint + 1);
            }
        } else {
            return "";
        }
    }


    private String getGitBranchNameByPath(String path) {

        if (path.indexOf(";") > 0) {
            path = StringUtils.deleteWhitespace(path.split(";")[0]);
        }
        if (path.indexOf("|") >= 0) {
            path = path.substring(path.indexOf("|") + 1);
            if (path.indexOf("|") > 0) {
                path = path.substring(0, path.indexOf("|"));
            }
            return path;
        } else {
            return "master";
        }


    }
}
