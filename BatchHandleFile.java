import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * Created by JINSHENGJIE on 2016/2/15.
 */
public class BatchHandleFile {

    public static void main(String[] args) {
/*        String dirName = "C:\\Users\\JINSHENGJIE\\Desktop\\action";
        String destName = "C:\\Users\\JINSHENGJIE\\Desktop\\新建文件夹";
        File sourceDir = new File(dirName);
        File destDir = new File(destName);
        String sourceCodeFormat = "BGK";
        String destCodeFormat = "UTF-8";
        try {
            batchChangeFileEncode(sourceDir, sourceCodeFormat, destDir, destCodeFormat);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /* ------------------------转换文件名称--------------------------  */
        String targetDir = "C:\\Users\\JINSHENGJIE\\Desktop\\新建文件夹";
        File targetFile = new File(targetDir);
        batchChangeFileName(targetFile, "SHA");
    }

    /**
     * 批量转换文件名称
     *
     * @param targetFile
     * @param KEY
     */
    private static void batchChangeFileName(File targetFile, final String KEY) {
        Collection<File> files = FileUtils.listFiles(targetFile, null, true);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY);
            BigInteger sha = null;
            for (File file : files) {
                String fileName = file.getName();
                String fileNamePrefix = fileName.split("\\.")[0];
                messageDigest.update(fileNamePrefix.getBytes());
                sha = new BigInteger(messageDigest.digest());
                String newNamePrefix = sha.toString(32);
                String newPath = file.getCanonicalPath().replaceAll(fileNamePrefix,newNamePrefix);
                file.renameTo(new File(newPath));

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量转码
     *
     * @param sourceDir
     * @param destDir
     * @throws IOException
     */
    private static void batchChangeFileEncode(File sourceDir, String sourceCodeFormat, File destDir, String destCodeFormat) throws IOException {
        Collection<File> sourceFiles = FileUtils.listFiles(sourceDir, new String[]{"java"}, true);
        for (File file : sourceFiles) {
            String newFilePath = destDir.getCanonicalPath() + file.getCanonicalPath().substring(sourceDir.getCanonicalPath().length());
            FileUtils.writeLines(new File(newFilePath), destCodeFormat, FileUtils.readLines(file, sourceCodeFormat));
        }
    }


}
