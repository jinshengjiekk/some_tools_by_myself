import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JINSHENGJIE on 2016/2/15.
 */
public class DeleteSpecificFiles {
    public static void main(String[] args) {
        String dirName="C:\\Users\\JINSHENGJIE\\Desktop\\一号导师\\mooc_bbs";
        File dir = new File(dirName);
        String result = deleteFiles(dir,"百纳科技源码.png","国内最大的免费商业源码下载平台.mht","源码说明.txt");
        System.out.println(result);
    }

    private static String  deleteFiles(File dir, String ... filenames) {
        if(!dir.isDirectory() | filenames.length==0){
            return "参数传递错误！";
        }
        Set set = new HashSet();
        for(String fileName:filenames){
            set.add(fileName);
        }

        File [] files = dir.listFiles();
        for(File file :files){
            if(file.isDirectory()){
                deleteFiles(file,filenames);
            }else{
                if(set.contains(file.getName())){
                    file.delete();
                }
            }
        }

        return "删除完成！";
    }
}
