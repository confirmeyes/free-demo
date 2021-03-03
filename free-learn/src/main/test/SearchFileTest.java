import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * @author WIN10 .
 * @create 2021-03-03-14:05 .
 * @description .
 */
public class SearchFileTest {


    @Test
    public void classifyFiles() {
        File originFile = new File("D:\\打印机夏普AR2048S");
        List<String> fileNames = getFileNames(new ArrayList<>(), originFile);
        Collections.sort(fileNames);
        //直接使用TreeMap,自动排序
        Map<String, List<String>> map = new TreeMap<>();
        fileNames.forEach(fileName -> {
            String ex = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!map.keySet().contains(ex)) {
                map.put(ex, new ArrayList<>());
            }
            map.get(ex).add(fileName);
        });
        System.out.println(JSONObject.toJSONString(map));
    }

    private List<String> getFileNames(List<String> list, File file) {
        File[] files = file.listFiles();
        if (files == null) return list;
        for (File file1 : files) {
            if (file1.isFile()) {
                list.add(file1.getName());
                continue;
            }
            list = getFileNames(list, file1);
        }
        return list;
    }

}
