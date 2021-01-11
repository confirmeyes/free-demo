package utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WIN10 .
 * @create 2020-07-06-18:33 .
 * @description .
 */
public class ExportExcelUtil {

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) throws Exception {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) throws Exception {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }


    public static void exportExcel(List<?> list, ExportParams exportParams, Class<?> pojoClass, String fileName, HttpServletResponse response) throws Exception {
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws Exception {
        defaultExport(list, fileName, response);
    }

    public static void exportExcel(Workbook workbook, String fileName, HttpServletResponse response) throws Exception {
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws Exception {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws Exception {
        String codedFileName = URLEncoder.encode(fileName, "UTF-8");
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName + ".xls");

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    private static void downLoadExcelMatchAgent(String fileName, HttpServletResponse response, Workbook
            workbook, String userAgent) throws Exception {
        String codedFileName = URLEncoder.encode(fileName, "UTF-8");
        try {
            if (userAgent.contains("firefox")) {
                response.reset();
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-Type", "application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName + ".xls");
            }
            //workbook.write(response.getOutputStream());

            OutputStream output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            output.close();
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }


    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse
            response) throws Exception {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }


    public static void exportExcelForMapList(List<Map<String, Object>> list, String
            fileName, HttpServletResponse response, String userAgent) throws Exception {

        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        ExcelExportEntity excelentity = new ExcelExportEntity("city", "city");
        excelentity.setNeedMerge(true);
        entity.add(excelentity);
        entity.add(new ExcelExportEntity("ci", "ci"));
        entity.add(new ExcelExportEntity("county", "county"));
        entity.add(new ExcelExportEntity("lon_lat_point", "lon_lat_point"));
        entity.add(new ExcelExportEntity("operator", "operator"));
        entity.add(new ExcelExportEntity("sinr", "sinr"));
        entity.add(new ExcelExportEntity("province", "province"));
        entity.add(new ExcelExportEntity("user_id", "user_id"));
        entity.add(new ExcelExportEntity("group_id", "group_id"));
        entity.add(new ExcelExportEntity("phone", "phone"));
        entity.add(new ExcelExportEntity("pci", "pci"));
        entity.add(new ExcelExportEntity("enb", "enb"));
        entity.add(new ExcelExportEntity("rscp", "rscp"));
        entity.add(new ExcelExportEntity("rssi", "rssi"));
        entity.add(new ExcelExportEntity("rsrq", "rsrq"));
        entity.add(new ExcelExportEntity("network_type", "network_type"));
        entity.add(new ExcelExportEntity("tac_lac", "tac_lac"));
        entity.add(new ExcelExportEntity("create_time", "create_time"));
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("众测点数据列表 总条数 - " + list.size(), "点数据"), entity, list);

        downLoadExcelMatchAgent(fileName, response, workbook, userAgent);
    }

    public static void writeExcelWithModel(OutputStream outputStream, String sheetName, List<? extends Object> dataList, Class<? extends Object> clazz, HttpServletResponse response) throws Exception {
        ExcelWriter writer = EasyExcel.write(outputStream, clazz).build();
        String codedFileName = URLEncoder.encode(sheetName, "UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName + ".xls");

        WriteSheet writeSheet = new WriteSheet();
        writeSheet.setSheetName(sheetName);
        writer.write(dataList, writeSheet);
        writer.finish();

        outputStream.flush();
        outputStream.close();
    }
}
