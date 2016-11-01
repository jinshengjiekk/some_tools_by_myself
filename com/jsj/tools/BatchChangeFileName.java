package jsj.tools;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JINSHENGJIE on 2016/2/15.
 */
public class BatchChangeFileName {

    public static void main(String[] args) {
        File fileDir = new File("C:\\Users\\JINSHENGJIE\\Desktop\\ChangeFileName");
        String result = null;
        try {
            result = changeFileName(fileDir);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(result);

    }

    /**
     * 批量修改文件文件名，采用加密的方式
     *
     * @param fileDir
     * @return
     * @throws IllegalArgumentException
     */
    private static String changeFileName(File fileDir) throws IllegalArgumentException {

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            if (!fileDir.isDirectory()) {
                throw new IllegalArgumentException("参数传递错误！");
            }
            ;
            File[] files = fileDir.listFiles();
            if (files.length == 0) {
                System.out.println(fileDir.getName() + "文件夹内没有相应的文件");
                return "请输入含有文件的文件夹";
            }
            for (File file : files) {
                if (file.isFile()) {
                    String filename = file.getName();
                    //                    String absolutePath = file.getAbsolutePath();
                    String nameprefix = filename.split("\\.")[0];
                    //                    String newPrefix = new BASE64Encoder().encodeBuffer(nameprefix.getBytes()).split("\\r")[0];
                    //                    String newPath = file.getPath().replaceAll(nameprefix, newPrefix);
                    byte[] bytes = md5.digest(nameprefix.getBytes());
                    StringBuffer sb = new StringBuffer();
                    for (byte b : bytes) {
                        int bt = b & 0xff;
                        if (bt < 16) {
                            sb.append(0);
                        }
                        sb.append(Integer.toHexString(bt));
                    }
                    String newPrefix = sb.toString();
                    String newPath = file.getPath().replaceAll(nameprefix, newPrefix);
                    file.renameTo(new File(newPath));
                } else {
                    changeFileName(file);
                }
            }
            return "转换文件名称完成！";

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ("加密方式错误");
        }
    }
}


