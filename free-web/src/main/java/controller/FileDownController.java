package controller;

import entity.Region;
import model.OperatorReform;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.ExportExcelUtil;
import utils.JDBCUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WIN10 .
 * @create 2020-05-15-11:50 .
 * @description .
 */

@RestController
public class FileDownController {

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws Exception {

        //获取要下载的路径
        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("/scripts/jquery-1.9.1.min.js");
        //通过io流对文件进行读写
        FileInputStream fileInputStream = new FileInputStream(realPath);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Disposition", "attachment;filename=jquery-1.9.1.min.js");
        return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
    }


    @GetMapping("/regionExcelExport")
    public ResponseEntity operatorExcelExport(HttpServletResponse response) {

        List<Region> reginList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //2.编写sql语句
            String sql = "select * from region";
            //3.获取执行sql语句对象
            pstmt = (PreparedStatement) conn.prepareStatement(sql);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Region region = new Region();

                region.setId(resultSet.getInt("id"));
                region.setPid(resultSet.getInt("pid"));
                region.setLevel(resultSet.getInt("level"));
                region.setLng(resultSet.getFloat("Lng"));
                region.setLat(resultSet.getFloat("Lat"));
                region.setCitycode(resultSet.getString("citycode"));
                region.setName(resultSet.getString("name"));
                region.setSname(resultSet.getString("sname"));
                region.setYzcode(resultSet.getString("yzcode"));
                region.setMername(resultSet.getString("mername"));
                region.setPinyin(resultSet.getString("pinyin"));
                reginList.add(region);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            JDBCUtils.closeResource(conn, pstmt, null);

        }

        String result = "";
        try {
            OutputStream output = response.getOutputStream();
            ExportExcelUtil.writeExcelWithModel(output, "全国行政区划", reginList, Region.class, response);
        } catch (
                Exception e) {
            result = "系统提示：Excel文件导出失败，原因：" + e.toString();
            e.printStackTrace();
        }
        System.out.println("-------" + reginList.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
