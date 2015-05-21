package com.alibaba.bigData.svnloader.engine;

import com.alibaba.bigData.svnloader.objects.SvnFile;
import com.alibaba.bigData.svnloader.utils.FileUtils;
import com.alibaba.bigData.svnloader.utils.Md5Utils;
import com.alibaba.bigData.svnloader.utils.NoOpEntityResolver;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNFileRevision;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-1-10
 * Time: 下午6:29
 * 获取svn文件的操作类
 */
public class SvnFileLoader {

    public static final String LOG_IN_SVN_FAIL = "登陆svn地址失败";


    private Configuration configration;

    private String svnRoot;
    private SVNRepository svnRepository;


    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public SvnFileLoader(Configuration configration) {
        this.configration = configration;
    }

    /**
     * 初始化方法
     */
    public void init(String svnRoot) {
        this.svnRoot = svnRoot;
    }

    /**
     * 登录验证
     */
    private boolean login() {
        setupLibrary();
        try {
            svnRepository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(this.svnRoot));
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(configration.getSvnUserName(), configration.getSvnPassWord());
            svnRepository.setAuthenticationManager(authManager);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 不同协议初始化版本库
     */
    private static void setupLibrary() {
        // 对于使用http：//和https
        DAVRepositoryFactory.setup();
        // 对于使用svn：//和svn+xxx：
        SVNRepositoryFactoryImpl.setup();
        // 对于使用file：//
        FSRepositoryFactory.setup();
    }


    /**
     * 根据svnlog获取svnfiles
     */
    public List<SvnFile> getSvnlogFiles() throws Exception {
        if (login()) {
            Collection logEntries = svnRepository.log(new String[]{""}, null, 0, -1, true, true);
            List<SvnFile> svnFileDOList = getSvnFiles(logEntries);
            return svnFileDOList;
        } else {
            throw new Exception(LOG_IN_SVN_FAIL + " " + this.svnRoot);
        }
    }

    /**
     * 根据logentry获取svnfile的list
     */
    public List<SvnFile> getSvnFiles(Collection logEntries) throws SVNException {
        List<SvnFile> svnFileDOList = new ArrayList<SvnFile>();
        for (Object logEntryObject : logEntries) {
            //获取svnlogentry
            SVNLogEntry logEntry = (SVNLogEntry) logEntryObject;
            Map<String, SVNLogEntryPath> changedPathMap = logEntry.getChangedPaths();
            for (SVNLogEntryPath svnLogEntryPath : changedPathMap.values()) {
                String svnChangeRelPath = svnLogEntryPath.getPath();
                String svnChagneAblPath = svnRepository.getRepositoryRoot(false).toString() + svnChangeRelPath;
                if (isInSvnRoot(svnChagneAblPath) && isXmlFile(svnChangeRelPath) && (isSrcFile(svnChagneAblPath) || isXmlRoot(svnRoot))) {
                    List<SvnFile> removeSvnFileDOList = new ArrayList<SvnFile>();
                    //校验文件是否已经存在
                    for (SvnFile svnFileDO : svnFileDOList) {
                        if (svnFileDO.getFileUrl().equals(svnChagneAblPath)) {
                            removeSvnFileDOList.add(svnFileDO);
                        }
                    }
                    svnFileDOList.removeAll(removeSvnFileDOList);
                    String svnChangeRefPath = getChangeRefPath(svnChagneAblPath, svnRoot);
                    //增加svnFileDO
                    if (svnLogEntryPath.getType() != 'D') {
                        SvnFile svnFileDO = new SvnFile();
                        svnFileDO.setAuthor(logEntry.getAuthor());
                        svnFileDO.setFileName(FileUtils.getFileName(svnChangeRelPath));
                        svnFileDO.setType(String.valueOf(svnLogEntryPath.getType()));
                        svnFileDO.setChangeDate(format.format(logEntry.getDate()));
                        svnFileDO.setRevision(logEntry.getRevision());
                        svnFileDO.setLogMessage(logEntry.getMessage());
                        svnFileDO.setFileUrl(svnChagneAblPath);
                        svnFileDO.setRepositoryRoot(svnRoot);
                        svnFileDO.setFileRelPath(svnChangeRefPath);
                        if (svnChangeRelPath.startsWith("/")) {
                            svnFileDO.setFilePath(svnChangeRelPath.substring(1));
                        } else {
                            svnFileDO.setFilePath(svnChangeRelPath);
                        }
                        svnFileDOList.add(svnFileDO);
                    }
                }
            }
        }
        //获取文件内容
        svnFileDOList = getSvnFileContentList(svnFileDOList);
        return svnFileDOList;
    }

    /**
     * 获取变化的照比提交地址的相对路径
     */
    private String getChangeRefPath(String svnChagneAblPath, String svnRoot) {
        return svnChagneAblPath.substring(svnChagneAblPath.indexOf(svnRoot) + svnRoot.length());
    }


    /**
     * 获取svnfile的内容
     */
    private List<SvnFile> getSvnFileContentList(List<SvnFile> svnFileDOList) {
        List<SvnFile> removeFileList = new ArrayList<SvnFile>();
        for (SvnFile svnFileDO : svnFileDOList) {
            //获取svn文件内容
            if (getSvnFileContent(svnFileDO, svnRepository) == null) {
                removeFileList.add(svnFileDO);
            }
        }
        svnFileDOList.removeAll(removeFileList);
        return svnFileDOList;

    }

    /**
     * 获取svn文件的内容
     */
    private SvnFile getSvnFileContent(SvnFile svnFileDO, SVNRepository svnRepository) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            SAXReader reader = new SAXReader();
            String file_path = svnFileDO.getFileRelPath();
            if (file_path.startsWith("/")) {
                file_path = file_path.substring(1);
            }
            svnRepository.getFile(file_path, -1, null, os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            reader.setValidation(false);
            reader.setEntityResolver(new NoOpEntityResolver());
            Document document = reader.read(is);
            Element root = document.getRootElement();
            if (FileUtils.isSqlMap(root)) {
                is.close();
                os.close();
                svnFileDO.setFileOsString(document.asXML());
                svnFileDO.setFileEncoiding(document.getXMLEncoding());
                svnFileDO.setFileMd5(Md5Utils.md5(document.asXML()));
                svnFileDO.setFileSize(document.asXML().length() / 1024);
                return svnFileDO;
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("404") <= 0) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据名字判断是否是xml
     */
    private boolean isXmlFile(String fileName) {
        if (fileName != null && fileName.length() > 4 && fileName.substring(fileName.length() - 4, fileName.length()).equalsIgnoreCase(".XML")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据文件路径判断是否是要选的文件
     */
    private boolean isSrcFile(String filePath) {
        if ((filePath.contains("/src/")) && (!filePath.contains("/spring/")) && (!filePath.contains("/WEB-INF/"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是单独提交的xml
     */
    private boolean isXmlRoot(String rootPath) {
        if (svnRoot != null && svnRoot.equalsIgnoreCase(".xml")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查地址是否在root下
     */
    private boolean isInSvnRoot(String svnChagneAblPath) {
        if (svnChagneAblPath.contains(svnRoot)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取分支文件对应的主干地址
     */
    public SvnFile getTrunkFile(String branchUrl) throws SVNException {
        String svnTrunkRoot = branchUrl;
        setupLibrary();
        SVNRepository svnTrunkRepository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnTrunkRoot));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(configration.getSvnUserName(), configration.getSvnPassWord());
        svnTrunkRepository.setAuthenticationManager(authManager);
        Collection revisions = new ArrayList();
        svnTrunkRepository.getFileRevisions("", revisions, 0, svnTrunkRepository.getLatestRevision());
        SvnFile truankFile = getTruankFileAndVersion(revisions, svnTrunkRepository);
        return truankFile;
    }


    private SvnFile getTruankFileAndVersion(Collection revisions, SVNRepository svnTrunkRepository) throws SVNException {
        SVNFileRevision trunkSvnfileRevision = null;
        long trunkRevision = -1;
        String trunkPath = "";
        SvnFile trunckFileDO = new SvnFile();
        for (Object revision : revisions) {
            SVNFileRevision svnFileRevision = (SVNFileRevision) revision;
            String path = getSvnPath(svnFileRevision, svnTrunkRepository);
            if (isTrunkFile(path)) {
                trunkSvnfileRevision = svnFileRevision;
                trunkRevision = ((SVNFileRevision) revision).getRevision();
                trunkPath = path;
            }
        }

        if (trunkRevision != -1 && trunkSvnfileRevision != null && !trunkPath.equals("")) {
            trunckFileDO.setFileName(FileUtils.getFileName(trunkPath));
            trunckFileDO.setRepositoryRoot(svnTrunkRepository.getRepositoryRoot(true).toString());
            trunckFileDO.setRevision(trunkRevision);
            trunckFileDO.setFilePath("");
            trunckFileDO.setFileUrl(trunkPath);
            trunckFileDO.setLogMessage(trunkSvnfileRevision.getRevisionProperties().getStringValue("svn:log"));
            trunckFileDO.setChangeDate(trunkSvnfileRevision.getRevisionProperties().getStringValue("svn:date"));
            trunckFileDO.setAuthor(trunkSvnfileRevision.getRevisionProperties().getStringValue("svn:author"));
            trunckFileDO = getSvnFileContent(trunckFileDO, svnTrunkRepository);
        }

        return trunckFileDO;
    }

    /**
     * 检查是否是主干地址
     */
    private boolean isTrunkFile(String path) {
        if (path != null && (path.indexOf("trunk") > 0) && (path.indexOf("branches") <= 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取svn地址
     */
    private String getSvnPath(SVNFileRevision revision, SVNRepository turnkSvnRepository) throws SVNException {
        return turnkSvnRepository.getRepositoryRoot(true).getProtocol() + "://"
                + turnkSvnRepository.getRepositoryRoot(true).getHost()
                + turnkSvnRepository.getRepositoryRoot(true).getPath()
                + revision.getPath();
    }


}
