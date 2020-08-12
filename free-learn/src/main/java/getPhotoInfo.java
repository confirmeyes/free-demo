import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

/**
 * @author lpx .
 * @create 2019-11-25-15:38 .
 * @description 获取图片地理信息.
 */


public class getPhotoInfo {


    public void readMetadata() {

        File jpegFile = new File("C:\\Users\\lpx\\Desktop\\417e9be7c54025e47dbb18722dd54e6.jpg");

        Metadata metadata;
        try {
            metadata = JpegMetadataReader.readMetadata(jpegFile);
            for (Directory exif : metadata.getDirectories()) {
                for (Tag tag : exif.getTags()) {
                    System.out.println(tag);
                }
            }
            System.out.println("图片信息读取完成！");
        } catch (JpegProcessingException | IOException e) {
            e.printStackTrace();
        }
    }

}
