package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author WIN10 .
 * @create 2020-05-15-11:49 .
 * @description .
 */

@RestController
public class FileUploadController {

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile[] multipartFile,
                         @RequestParam(value = "desc", required = false) String desc) throws IOException {

        System.out.println(desc);
        //获取文件的名称
        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                System.out.println(file.getOriginalFilename());
                file.transferTo(new File("d:\\file\\" + file.getOriginalFilename()));
            }
        }
        return "success";
    }
}
