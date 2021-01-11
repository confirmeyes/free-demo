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

    public static void main(String[] args) {
        readMetadata();
    }


    public static void readMetadata() {

        File jpegFile = new File("C:\\Users\\WIN10\\Desktop\\mmexport1605584497513.jpg");
//IMG_20201117_113154.jpg
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
