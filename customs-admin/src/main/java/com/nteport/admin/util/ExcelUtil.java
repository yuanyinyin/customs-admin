package com.nteport.admin.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ExcelUtil {
    /**
     * Excel导出
     *
     * @param response    响应
     * @param fileName    文件名
     * @param columnList  每列的标题名
     * @param dataList    导出的数据
     * @param columnWidth 每列宽度
     */
    public static void exportExcel(HttpServletResponse response, String fileName, List<String> columnList, List<List<String>> dataList, int columnWidth) {
        //声明输出流
        OutputStream os = null;
        //设置响应头
        setResponseHeader(response, fileName);
        try {
            //获取输出流
            os = response.getOutputStream();
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
            //获取该工作区的第一个sheet
            Sheet sheet1 = wb.createSheet("sheet1");
            int excelRow = 0;
            //创建标题行
            Row titleRow = sheet1.createRow(excelRow++);
            for (int i = 0; i < columnList.size(); i++) {
                //创建该行下的每一列，并写入标题数据
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnList.get(i));
                sheet1.setColumnWidth(i, columnWidth);
            }
            //设置内容行
            if (dataList != null && dataList.size() > 0) {
                //序号是从1开始的
                int count = 1;
                //外层for循环创建行
                for (int i = 0; i < dataList.size(); i++) {
                    Row dataRow = sheet1.createRow(excelRow++);
                    //内层for循环创建每行对应的列，并赋值
                    for (int j = 0; j < dataList.get(0).size(); j++) {
                        Cell cell = dataRow.createCell(j);
                        cell.setCellValue(dataList.get(i).get(j));
                    }
                }
            }
            //将整理好的excel数据写入流中
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        设置浏览器下载响应头
     */
    private static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
