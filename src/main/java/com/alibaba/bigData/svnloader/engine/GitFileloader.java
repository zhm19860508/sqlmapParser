package com.alibaba.bigData.svnloader.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.PathSuffixFilter;

import com.alibaba.bigData.svnloader.objects.GitFile;
import com.alibaba.bigData.svnloader.utils.FileUtils;
import com.alibaba.bigData.svnloader.utils.Md5Utils;
import com.alibaba.bigData.svnloader.utils.NoOpEntityResolver;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-1-14
 * Time: 上午10:30
 * git的template操作类
 */
public class GitFileloader {

    public static final String UNKONW_FILEMODE = "UNKONW FILEMODE";

    private Configuration gitConfiguration;
    private String gitRoot;
    private String branchName;

    CredentialsProvider credentialsProvider;
    String tmpGitPath;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public GitFileloader(Configuration gitConfiguration) {
        this.gitConfiguration = gitConfiguration;
    }

    /**
     * 初始化方法
     */
    public void init(String gitRoot, String branchName) {
        this.branchName = branchName;
        this.gitRoot = gitRoot;
        if (StringUtils.isNotEmpty(gitConfiguration.getGitUserName())&&StringUtils.isNotEmpty(gitConfiguration.getGitPassWord()) ) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(gitConfiguration.getGitUserName(), gitConfiguration.getGitPassWord());
        }
    }

    /**
     * 获取git对象
     */
    public Git getTmpGit() throws GitAPIException {
        File gitFolderUrl = new File("/tmp/git" + new Date().getTime());
        this.tmpGitPath = gitFolderUrl.getAbsolutePath();
        UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(gitConfiguration.getGitUserName(), gitConfiguration.getGitPassWord());
        Git git = Git.cloneRepository()
                .setURI(gitRoot)
                .setDirectory(gitFolderUrl)
                .setCredentialsProvider(user)
                        //.setNoCheckout(true)
                .setBranch(branchName).call();
        return git;
    }

    /**
     * 获取git
     */
    public List<GitFile> getGitFiles() throws GitAPIException, IOException {
        List<GitFile> gitFileList = new ArrayList<GitFile>();
        Git git = getTmpGit();
        for (RevCommit revCommit : git.log().call()) {
//            RevCommit parent = null;
//            if (revCommit.getParent(0) != null) {
//                RevWalk revWalk = new RevWalk(git.getRepository());
//                parent = revWalk.parseCommit(revCommit.getParent(0).getId());
//            }
//            DiffFormatter df = new DiffFormatter(System.out);
//            df.setRepository(git.getRepository());
//            df.setDiffComparator(RawTextComparator.DEFAULT);
//            df.setDetectRenames(true);
//            List<DiffEntry> diffs = df.scan(parent.getTree(), revCommit.getTree());
//            for (DiffEntry diff : diffs) {
//                df.format(diff);
//                System.out.println("changeType=" + diff.getChangeType().name()
//                        + " newMode=" + diff.getNewMode().getBits()
//                        + " newPath=" + diff.getNewPath());
//            }
            RevTree revTree = revCommit.getTree();
            TreeWalk treeWalk = new TreeWalk(git.getRepository());
            treeWalk.addTree(revTree);
            treeWalk.setFilter(PathSuffixFilter.create(".xml"));
            if (gitConfiguration.getPathFilter() != null) {
                treeWalk.setFilter(PathFilter.create(gitConfiguration.getPathFilter()));
            }
            if (gitConfiguration.getSuffixFilter() != null) {
                treeWalk.setFilter(PathSuffixFilter.create(gitConfiguration.getSuffixFilter()));
            }
            treeWalk.setRecursive(true);
            while (treeWalk.next()) {
                GitFile gitFile = new GitFile();
                gitFile.setObjectId(revCommit.getId().toString());
                gitFile.setAuthor(revCommit.getAuthorIdent().getName());
                gitFile.setChangeDate(getChangeDate(revCommit.getCommitTime()));
                gitFile.setFilePath(treeWalk.getPathString());
                gitFile.setFileName(FileUtils.getFileName(treeWalk.getPathString()));
                gitFile.setRepositoryRoot(gitRoot);
                gitFile.setBranchName(branchName);
                gitFile.setLogMessage(revCommit.getFullMessage());
                gitFile.setRevision(revCommit.getName());
                //获取filemode
                FileMode fileMode = treeWalk.getFileMode(0);
                gitFile.setFileMode(getFileMode(fileMode));
                if (fileMode.getObjectType() == 3) {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ObjectLoader loader = git.getRepository().open(treeWalk.getObjectId(0));
                    loader.copyTo(os);
                    gitFile = getXmlFileContent(os, gitFile);
                    if (gitFile != null) {
                        //插入不重复文件
                        boolean addGitFile = true;
                        for (GitFile addGitFileDO : gitFileList) {
                            if (addGitFileDO.getFilePath().equals(gitFile.getFilePath())) {
                                addGitFile = false;
                            }
                        }
                        if (addGitFile) {
                            if (new File(tmpGitPath + "/" + treeWalk.getPathString()).isFile()) {
                                gitFileList.add(gitFile);
                            }
                        }
                    }
                }
            }

        }
        return gitFileList;
    }

    private GitFile getXmlFileContent(ByteArrayOutputStream os, GitFile gitFile) {
        try {
            SAXReader reader = new SAXReader();
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            reader.setValidation(false);
            reader.setEntityResolver(new NoOpEntityResolver());
            Document document = reader.read(is);
            Element root = document.getRootElement();
            if (FileUtils.isSqlMap(root)) {
                is.close();
                os.close();
                gitFile.setFileOsString(document.asXML());
                gitFile.setFileEncoiding(document.getXMLEncoding());
                gitFile.setFileMd5(Md5Utils.md5(document.asXML()));
                gitFile.setFileSize(document.asXML().length() / 1024);
                return gitFile;
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("404") <= 0) {
                //e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 返回commit时间
     */
    private String getChangeDate(long commitTime) {
        long time = commitTime * 1000;
        Date date = new Date();
        date.setTime(time);
        return format.format(date);
    }

    /**
     * 获取所有分支
     */
    public void getAllBranchList() throws GitAPIException {
        Git git = getTmpGit();
        List<Ref> call = git.branchList().call();
        for (Ref ref : call) {
            System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
        }
    }


    /**
     * 获取文件类型
     */
    private String getFileMode(FileMode fileMode) {
        if (fileMode.equals(FileMode.EXECUTABLE_FILE)) {
            return "Executable File";
        } else if (fileMode.equals(FileMode.REGULAR_FILE)) {
            return "Normal File";
        } else if (fileMode.equals(FileMode.TREE)) {
            return "Directory";
        } else if (fileMode.equals(FileMode.SYMLINK)) {
            return "Symlink";
        } else {
            throw new IllegalArgumentException(UNKONW_FILEMODE + fileMode);
        }
    }


}
