package controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import model.OperatorReform;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
                file.getInputStream();
            }
        }
        return "success";
    }


    @PostMapping("/reform/operatorExcelImport")
    public ResponseEntity operatorExcelImport(@RequestParam("file") MultipartFile multipartFile,
                                              @RequestParam("operator") String operator,
                                              @RequestParam("province") String province) {
        String result = "";
        try {
            List<OperatorReform> reformList = getReformList(multipartFile, operator, 1L);
            reformList.remove(0);
            JSONObject requestJson = new JSONObject();
            requestJson.put("reformList", reformList);
            requestJson.put("province", province);
            return new ResponseEntity<>(requestJson, HttpStatus.OK);
        } catch (Exception e) {
            result = "系统提示：Excel文件导入失败，原因：" + e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    private List<OperatorReform> getReformList(MultipartFile multipartFile, String operator, Long userId) throws Exception {
        ImportParams params = new ImportParams();
        List<OperatorReform> reformList = ExcelImportUtil.importExcel(multipartFile.getInputStream(), OperatorReform.class, params);
        /*ExcelImportUtil.importExcelBySax(
                multipartFile.getInputStream(),
                OperatorReform.class, params, new IReadHandler<OperatorReform>() {
                    @Override
                    public void handler(OperatorReform o) {
                        reformList.add(o);
                    }

                    @Override
                    public void doAfterAll() {
                        System.out.println("全部执行完毕了--------------------------------");
                        log.info("基站个数: {} ", reformList.size());
                    }
                });*/
        reformList.stream()
                .map(reform -> reform.setOperator(operator).setUserId(userId))
                .collect(Collectors.toList());
        return reformList;
    }
}
