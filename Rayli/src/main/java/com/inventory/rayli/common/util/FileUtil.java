//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inventory.rayli.common.util;

import com.inventory.rayli.common.bo.FileSaveInfo;
import com.inventory.rayli.common.vo.BusinessException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static final String TEMP_FILE_NAME = "temp";
    public static final String UPLOAD_FILE_NAME = "upload";
    public static final String BACKUP_FILE_NAME = "backup";
    public static final String FILE_ROOT_NAME = "files";
    public static final String DISK_SWITCH_NAME = "disk_";
    private static final long DISK_SWITCH_RESERVED = 1073741824L;
    private static final String JAR_SUFFIX = ".jar";
    private static final String JAR_PREFIX = "jar:";
    private static final String FILE_PREFIX = "file:";
    private static final String CLASSPATH_PREFIX = "classpath:";
    private static final String HTTP_PREFIX = "http://";
    private static final String BOOT_INF = "BOOT-INF";
    private static final String CLASSES = "classes";
    private static final String TEST_CLASSES = "test-classes";

    public FileUtil() {
    }

    public static void download(HttpServletResponse response, InputStream inputStream, String fileName) throws Exception {
        fileName = fileName.replace(" ", "%20");
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            boolean var7 = false;

            int size;
            while((size = bis.read(buff)) > -1) {
                bos.write(buff, 0, size);
            }
        } catch (Exception var11) {
            throw var11;
        } finally {
            if (bis != null) {
                bis.close();
            }

            if (bos != null) {
                bos.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

    }

    public static void download(HttpServletResponse response, File file, String fileName) throws Exception {
        InputStream in = new FileInputStream(file);
        download(response, (InputStream)in, fileName);
    }

    public static void download(HttpServletResponse response, String filePath, String fileName) throws Exception {
        InputStream in = new FileInputStream(filePath);
        download(response, (InputStream)in, fileName);
    }

    public static File string2File(String res, String filePath) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        File distFile = new File(filePath);

        File var6;
        try {
            if (!distFile.getParentFile().exists()) {
                distFile.getParentFile().mkdirs();
            }

            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char[] buff = new char[1024];

            int len;
            while((len = bufferedReader.read(buff)) != -1) {
                bufferedWriter.write(buff, 0, len);
            }

            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
            return distFile;
        } catch (IOException var16) {
            var16.printStackTrace();
            var6 = distFile;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return var6;
    }

    public static String getImageStr(String imgPth) {
        return getImageBase64(new File(imgPth));
    }

    public static String getImageBase64(File imgFile) {
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static void writeStringToTxt(String str, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(str.getBytes());
            fos.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static String readTxtFile(String filePath) throws IOException {
        String encoding = "GBK";
        String result = readFile(filePath, encoding);
        return result;
    }

    public static String readFile(InputStream inputStream, String encoding) throws IOException {
        byte[] b = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean var4 = true;

        int len;
        while((len = inputStream.read(b)) != -1) {
            out.write(b, 0, len);
        }

        out.close();
        inputStream.close();
        String str = new String(out.toByteArray(), encoding);
        return str;
    }

    public static String readFile(String filePath, String encoding) throws IOException {
        String result = "";
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);

            for(String lineTxt = null; (lineTxt = bufferedReader.readLine()) != null; result = result + lineTxt + "\n") {
            }

            read.close();
            return result;
        } else {
            throw new BusinessException("文件【" + filePath + "】不存在");
        }
    }

    public static void deleteTempFile() {
        File tempRootFolder = new File(getStaticFileFolder(), "temp");
        int max = 5;
        if (tempRootFolder.exists() && tempRootFolder.list().length > 1) {
            while(tempRootFolder.list().length > max) {
                logger.info("临时文件目录：" + String.join(",", tempRootFolder.list()));
                String first = tempRootFolder.list()[0];
                String last = tempRootFolder.list()[tempRootFolder.list().length - 1];
                String del = first;
                if (RegExpValidator.isNumber(last) && RegExpValidator.isNumber(first) && Integer.parseInt(last) < Integer.parseInt(first)) {
                    del = last;
                }

                logger.info("删除临时文件：" + del);
                File childrenFile = new File(tempRootFolder, del);
                deleteFile(childrenFile);
            }
        }

    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                String folderPath = file.getAbsolutePath();
                String[] var2 = file.list();
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    String fileName = var2[var4];
                    File childrenFile = new File(folderPath + File.separator + fileName);
                    deleteFile(childrenFile);
                }
            }

            file.delete();
        }
    }

    private static InputStream loadResourceFromJarUrl(String jarUrl, String resPath) {
        if (!jarUrl.endsWith(".jar")) {
            return null;
        } else {
            if (jarUrl.indexOf("file:") == 0) {
                jarUrl = jarUrl.substring(6);
            }

            URL url = null;
            if (jarUrl.startsWith("http://")) {
                try {
                    url = new URL("jar:" + jarUrl + "!/");
                } catch (MalformedURLException var8) {
                    var8.printStackTrace();
                    return null;
                }
            } else {
                try {
                    url = new URL("jar:file:/" + jarUrl + "!/");
                } catch (MalformedURLException var7) {
                    var7.printStackTrace();
                    return null;
                }
            }

            try {
                JarURLConnection urlConnection = (JarURLConnection)url.openConnection();
                JarFile jarFile = urlConnection.getJarFile();
                JarEntry jarEntry = jarFile.getJarEntry(resPath.replace("\\", "/"));
                if (jarEntry == null) {
                    logger.info("未读取到jar中的文件信息");
                    return null;
                } else {
                    return jarFile.getInputStream(jarEntry);
                }
            } catch (IOException var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public static String getResourceFilePath(String templateName) throws IOException {
        File classpathFile = new File(ResourceUtils.getURL("classpath:").getPath());
        InputStream inputStream = null;
        String type;
        if (classpathFile.getAbsolutePath().indexOf("file:") > 0) {
            type = classpathFile.getAbsolutePath().substring(classpathFile.getAbsolutePath().indexOf("file:"));
            type = type.replace("!" + File.separator + "BOOT-INF" + File.separator + "classes!", "");
            inputStream = loadResourceFromJarUrl(type, "BOOT-INF" + File.separator + "classes" + File.separator + templateName);
        } else if (classpathFile.getAbsolutePath().indexOf("test-classes") > 0) {
            File[] jarFiles = classpathFile.getParentFile().listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar");
                }
            });
            File[] var4 = jarFiles;
            int var5 = jarFiles.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File jarFile = var4[var6];
                inputStream = loadResourceFromJarUrl(jarFile.getAbsolutePath(), "BOOT-INF" + File.separator + "classes" + File.separator + templateName);
                if (inputStream != null) {
                    break;
                }
            }
        } else {
            ClassPathResource classPathResource = new ClassPathResource(templateName);

            try {
                inputStream = classPathResource.getInputStream();
            } catch (Exception var12) {
                throw new BusinessException("资源文件不存在", var12);
            }
        }

        type = templateName.substring(templateName.lastIndexOf("."));
        File tempFile = new File(getTempFolderTime(), UUID.randomUUID().toString() + type);
        if (inputStream != null) {
            try {
                FileUtils.copyInputStreamToFile(inputStream, tempFile);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }

        return tempFile.getAbsolutePath();
    }

    public static File getStaticFileFolder() {
        String webappRoot = System.getProperty("user.dir");
        File file = new File(webappRoot, "files");
        return file;
    }

    public static String getTempFolder() {
        File tempFolder = new File(getStaticFileFolder(), "temp");
        deleteTempFile();
        return tempFolder.getAbsolutePath().concat(File.separator);
    }

    public static String getBackupFolder() {
        File file = new File(getStaticFileFolder(), "backup");
        return file.getAbsolutePath().concat(File.separator);
    }

    public static String getTempUrl(String absolutePath) {
        return getFileRelativeUrl(absolutePath);
    }

    public static String getUploadUrl(String absolutePath) {
        return getFileRelativeUrl(absolutePath);
    }

    private static String getFileRelativeUrl(String absolutePath) {
        String parentFolder = getStaticFileFolder().getAbsolutePath();
        if (absolutePath.indexOf(parentFolder) == -1) {
            File[] roots = File.listRoots();

            for(int i = 0; i < roots.length; ++i) {
                File root = new File(roots[i], "files");
                if (absolutePath.indexOf(root.getAbsolutePath()) > -1) {
                    parentFolder = root.getAbsolutePath();
                    break;
                }
            }
        }

        String path = absolutePath.replace(parentFolder, "");
        return path;
    }

    public static String getTempFolderTime() {
        String day = DateConvertUtil.format(new Date(), "yyyyMMdd");
        String relativeFolder = "temp".concat(File.separator).concat(day);
        File folder = new File(getStaticFileFolder(), relativeFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        deleteTempFile();
        return folder.getAbsolutePath();
    }

    public static String getBackupFolderTime(Long fileSize) {
        String day = DateConvertUtil.format(new Date(), "yyyyMMdd");
        FileSaveInfo saveInfo = diskSwitch("backup", day, fileSize);
        File folder = new File(saveInfo.getParentFolder(), saveInfo.getRelativeUrl());
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder.getAbsolutePath();
    }

    public static String getBackupFolderTime() {
        return getBackupFolderTime((Long)null);
    }

    public static String getTimeFolder(String srcPath) {
        String day = DateConvertUtil.format(new Date(), "yyyyMMdd");
        String destPath = srcPath + day + File.separator;
        File file = new File(destPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return destPath;
    }

    public static String getUploadFileFolder() {
        File file = new File(getStaticFileFolder(), "upload");
        return file.getAbsolutePath().concat(File.separator);
    }

    public static String getUploadFileFolderTime() {
        return getUploadFileFolderTime((Long)null);
    }

    public static String getUploadFileFolderTime(Long fileSize) {
        String day = DateConvertUtil.format(new Date(), "yyyyMMdd");
        FileSaveInfo saveInfo = diskSwitch("upload", day.substring(0, 4).concat(File.separator).concat(File.separator + day.substring(0, 6)).concat(File.separator).concat(day), fileSize);
        File folder = new File(saveInfo.getParentFolder(), saveInfo.getRelativeUrl());
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder.getAbsolutePath();
    }

    public static String getSourceProjectPath() {
        String projectPath = "";

        try {
            File file = new File(ResourceUtils.getURL("classpath:").getPath());
            if (file.getAbsolutePath().indexOf("file:") > 0) {
                File binFolder = new File(file.getAbsolutePath().split("file:")[0]);
                File webSourceFolder = new File(binFolder.getParentFile().getAbsolutePath());
                projectPath = webSourceFolder.getAbsolutePath();
            } else if (file.getAbsolutePath().indexOf("test-classes") > 0) {
                projectPath = file.getParentFile().getParentFile().getAbsolutePath();
            } else {
                projectPath = file.getParentFile().getParentFile().getAbsolutePath();
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return projectPath;
    }

    public static void copyFile(File file, String desPath) {
        if (file.exists() && file.isFile()) {
            if (file.length() == 0L) {
                throw new BusinessException("【" + file.getAbsolutePath() + "】是空文件");
            } else {
                File desFile = new File(desPath);
                if (!desFile.getParentFile().exists()) {
                    desFile.getParentFile().mkdirs();
                }

                try {
                    FileUtils.copyFile(file, desFile);
                } catch (IOException var4) {
                    throw new BusinessException("文件复制失败", var4);
                }
            }
        } else {
            throw new BusinessException("文件【" + file.getAbsolutePath() + "】不存在");
        }
    }

    public static void copyNio(String source, File dest) {
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        FileChannel input = null;
        FileChannel output = null;

        try {
            input = (new FileInputStream(new File(source))).getChannel();
            output = (new FileOutputStream(dest)).getChannel();
            output.transferFrom(input, 0L, input.size());
        } catch (Exception var8) {
            logger.error("copyNio", "error occur while copy", var8);
        } finally {
            if (input != null) {
            }

            safelyClose(input);
            safelyClose(output);
        }

    }

    public static void copyFileUsingFileStreams(InputStream input, File dest) throws IOException {
        FileOutputStream output = null;

        try {
            if (dest.exists() && dest.length() == 0L) {
                dest.delete();
            }

            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];

            int bytesRead;
            while((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            safelyClose(input);
            safelyClose((OutputStream)output);
        }

    }

    private static void safelyClose(FileChannel stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException var2) {
                logger.error("safelyClose", var2);
            }
        }

    }

    private static void safelyClose(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException var2) {
                logger.error("safelyClose", var2);
            }
        }

    }

    private static void safelyClose(OutputStream stream) {
        if (stream != null) {
            try {
                stream.flush();
                stream.close();
            } catch (IOException var2) {
                logger.error("safelyClose", var2);
            }
        }

    }

    public static void uploadFile(MultipartFile file, String desPath) throws IOException {
        File desFile = new File(desPath);
        if (!desFile.getParentFile().exists()) {
            desFile.getParentFile().mkdirs();
        }

        FileOutputStream outputStream = new FileOutputStream(desFile);
        outputStream.write(file.getBytes());
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }

    }

    public static File uploadFile(MultipartFile file) throws IOException {
        String md5 = DigestUtils.md5Hex(file.getBytes());
        File outFile = new File(getUploadFileFolderTime(), md5.concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))));
        uploadFile(file, outFile.getAbsolutePath());
        return outFile;
    }

    public static File uploadTempFile(MultipartFile file) throws IOException {
        String md5 = DigestUtils.md5Hex(file.getBytes());
        File outFile = new File(getTempFolderTime(), md5.concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))));
        uploadFile(file, outFile.getAbsolutePath());
        return outFile;
    }

    public static byte[] uploadBase64File(String base64File, File outFile) throws IOException {
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }

        String[] arr = base64File.split(",");
        String baseStr = arr[0];
        if (arr.length > 1) {
            baseStr = arr[1];
        }

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(baseStr);

        for(int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] = (byte)(b[i] + 256);
            }
        }

        getFileByBytes(b, outFile);
        return b;
    }

    public static void downloadHttpUrl(String url, String dir, String fileName) {
        try {
            URL httpUrl = new URL(url);
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            FileUtils.copyURLToFile(httpUrl, new File(dir, fileName));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static String getFileSize(Long fileSize) {
        if (fileSize == null) {
            return "0";
        } else {
            String unit = "KB";
            double size = (double)fileSize * 1.0D / 1024.0D;
            if (size > 1024.0D) {
                size /= 1024.0D;
                unit = "MB";
                if (size > 1024.0D) {
                    size /= 1024.0D;
                    unit = "GB";
                }
            }

            return Math.floor(size * 100.0D) / 100.0D + unit;
        }
    }

    public static void downFile(HttpServletResponse response, byte[] bytes, String fileName) throws Exception {
        try {
            if (bytes != null) {
                OutputStream outputStream = response.getOutputStream();
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/x-download");
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                String userAgent = request.getHeader("User-Agent");
                byte[] byteName = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
                fileName = new String(byteName, "ISO-8859-1");
                response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
                InputStream is = new ByteArrayInputStream(bytes);
                byte[] buff = new byte[1024];
                boolean var9 = false;

                int len;
                while((len = is.read(buff)) != -1) {
                    outputStream.write(buff, 0, len);
                }

                is.close();
                outputStream.close();
            }

        } catch (Exception var10) {
            throw var10;
        }
    }

    public static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len;
            while((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }

            baos.flush();
            return baos;
        } catch (IOException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] getBytesByFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];

            int n;
            while((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static void getFileByBytes(byte[] bytes, File file) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;

        try {
            if (!file.getParentFile().exists() && file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

    }

    private static FileSaveInfo diskSwitch(String folderType, String timeFolder, Long reserved) {
        if (reserved == null || reserved < 1073741824L) {
            reserved = 1073741824L;
        }

        FileSaveInfo saveInfo = new FileSaveInfo();
        saveInfo.setParentFolder(getStaticFileFolder());
        saveInfo.setUserPath(timeFolder);
        saveInfo.setRelativeUrl(folderType + File.separator + saveInfo.getUserPath());
        File targetFolder = new File(saveInfo.getParentFolder(), saveInfo.getRelativeUrl());
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        long freeSpace = targetFolder.getUsableSpace();
        if (freeSpace < reserved) {
            boolean isSwitch = false;
            File[] roots = File.listRoots();

            for(int i = 0; i < roots.length; ++i) {
                long diskFreeSpace = roots[i].getUsableSpace();
                File root = new File(roots[i], "files");
                if (diskFreeSpace > reserved) {
                    saveInfo.setParentFolder(root);
                    saveInfo.setUserPath("disk_" + i + File.separator + timeFolder);
                    saveInfo.setRelativeUrl(folderType + File.separator + saveInfo.getUserPath());
                    targetFolder = new File(saveInfo.getParentFolder(), saveInfo.getRelativeUrl());
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }

                    isSwitch = true;
                    break;
                }
            }

            if (!isSwitch) {
                logger.info("磁盘剩余空间不足，切换磁盘失败，请即使清理磁盘");
            }
        }

        return saveInfo;
    }

    public static File getUploadFileByRelative(String userPath) {
        return getFileByRelative("upload".concat(File.separator).concat(userPath));
    }

    public static File getFileByRelative(String userPath) {
        File file = new File(getStaticFileFolder(), userPath.replace("\\", File.separator));
        if (!file.exists() && userPath.indexOf("disk_") > -1) {
            File[] roots = File.listRoots();

            for(int i = 0; i < roots.length; ++i) {
                file = new File(roots[i], "files".concat(File.separator).concat(userPath));
                if (file.exists()) {
                    return file;
                }
            }
        }

        return file;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").indexOf("Windows") != -1;
    }
}
