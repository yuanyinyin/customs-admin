package com.nteport.admin.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 薛卫 on 2018/11/19.
 */
public class DecHeadDownBySXXFExcelHelper {

    int hbCol=0;
    SXSSFSheet sheet=null;
    int rowaccess=50;//内存中缓存记录行数
    public SXSSFWorkbook wb =null;
    CellStyle topStyle;
    CellStyle headerStyle;
    CellStyle dateStyle;
    CellStyle cell_Style;
    public File newFile = null;
    public InputStream fis = null;
    FileOutputStream fos = null;

    public void init(HttpServletRequest request){
        newFile = createNewFile(request);

        try {
            fis = new FileInputStream(newFile);// 将excel文件转为输入流
            XSSFWorkbook xssfWb = new XSSFWorkbook(fis);
            wb = new SXSSFWorkbook(xssfWb,rowaccess);
            sheet = wb.getSheet("sheet1");
            topStyle = createHeaderStyle(wb);
            headerStyle = createTitleStyle(wb);
            dateStyle = createDateStyle(wb);
            cell_Style = createCellStyle(wb);
            fos = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToExcel() throws IOException {
        wb.write(fos);
    }

    public void closeInputStream(){
        try {
            fos.flush();
            fos.close();
            if (null != fis) {
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     *
     * @return
     */
    public File createNewFile(HttpServletRequest request) {
        // 读取模板，并赋值到新文件************************************************************
        // 文件模板路径
        //System.out.println(getExcelTemplate() + "custom_export_template.xlsx");
        String projectPath = request.getSession().getServletContext().getRealPath("")+"/platform/downBgd/decdown_export_template.xlsx";

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/decdown_export_template.xlsx");

        //String projectPath = "d:/test/excel/ss.xlsx";
        //String path = (getExcelTemplate() + "custom_export_template.xlsx");
        File file = new File(projectPath);
        // 保存文件的路径
        String realPath = (request.getSession().getServletContext().getRealPath("")+"/platform/downBgd/" + "bak");
        //String realPath = "d:/test/excel/bak";
                // 新的文件名
        String newFileName = "decdown_export_template" + System.currentTimeMillis() + ".xlsx";
        // 判断路径是否存在
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 写入到新的excel
        File newFile = new File(realPath, newFileName);
        try {
            newFile.createNewFile();
            // 复制模板到新文件
//            fileChannelCopy(file, newFile);
            fileChannelCopy(inputStream, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 复制文件
     *
     * @param s
     *            源文件
     * @param t
     *            复制到的新文件
     */
    public void fileChannelCopy(File s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fileChannelCopy(InputStream in, File t) {
        try {
//            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(in, 1024);
                out = new FileOutputStream(t);
                FileCopyUtils.copy(in, out);
//                byte[] buffer = new byte[1024];
//                int len;
//                while ((len = in.read(buffer)) != -1) {
//                    out.write(buffer, 0, len);
//                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void setHeader(String ieFlag){
        Row row = sheet.createRow((int) 0);
        row.setHeightInPoints(30);
        Cell topcell = row.createCell(0);
        if("E".equals(ieFlag)){
            topcell.setCellValue("出口报关单汇总");
            topcell.setCellStyle(topStyle);
        }else if("I".equals(ieFlag)){
            topcell.setCellValue("进口报关单汇总");
            topcell.setCellStyle(topStyle);
        }else{
            topcell.setCellValue("进/出口报关单汇总");
            topcell.setCellStyle(topStyle);
        }
        CellRangeAddress rowc =new CellRangeAddress(0, 0, 0, 21); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(rowc);
    }

    public void setTitle(String ieFlag,List<ExportHeads> list){
        Row row = sheet.createRow((int) 2);
        sheet.setDefaultColumnWidth(10);
        row.setHeightInPoints(26);
        Cell cell2 = row.createCell(0);
        cell2.setCellValue("序号");
        cell2.setCellStyle(headerStyle);
        for(ExportHeads e : list){
            Cell cell = row.createCell(e.getCol());
            cell.setCellValue(e.getName());
            cell.setCellStyle(headerStyle);
        }
    }

    public void setDateContent(String startTime,String endTime){
        if(ObjectUtils.isNotEmpty(startTime) || ObjectUtils.isNotEmpty(endTime)){
            String _d = "Date : ";
            _d += (ObjectUtils.isEmpty(startTime) ? "" : startTime) + "~" +(ObjectUtils.isEmpty(endTime) ? "" : endTime);
            Row row = sheet.createRow((int) 1);
            row.setHeightInPoints(26);
            Cell datecell = row.createCell(0);
            datecell.setCellValue(_d);
            datecell.setCellStyle(dateStyle);
            CellRangeAddress rowcd =new CellRangeAddress(1, 1, 0, 3); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(rowcd);
        }
    }

    public void setColumnContent(List<HashMap<String ,Object>> lists, List<ExportHeads> exportHeads,String export_isMerge) throws IOException {
        String old = "";
        for(int i=0;i<lists.size();i++) {
            HashMap<String, Object> m = lists.get(i);
            Row row = sheet.createRow(i + 3);
            row.setHeightInPoints(20);
            row.createCell(0).setCellValue(i + 1);//序号

            String ENTRYID = m.get("ENTRYID")==null?"":m.get("ENTRYID").toString();
            if(old.equals(ENTRYID)){
                for(ExportHeads e : exportHeads){
                    if(e.getCol() <= hbCol && "T".equals(export_isMerge)){
                        row.createCell(e.getCol()).setCellValue("");//
                    }else{
                        row.createCell(e.getCol()).setCellValue(m.get(e.getFieldName())==null?"":m.get(e.getFieldName()).toString());//商品名称
                    }
                }
            }else{
                for(ExportHeads e : exportHeads){
                    if(e.getFieldName().equals("FEERATE") || e.getFieldName().equals("INSURRATE") || e.getFieldName().equals("OTHERRATE")){
                        String r = m.get(e.getFieldName())==null?"":m.get(e.getFieldName()).toString();
                        if(r.equals("//")){
                            row.createCell(e.getCol()).setCellValue("");
                        }else{
                            row.createCell(e.getCol()).setCellValue(r);//运保杂特殊
                        }
                    }else{
                        row.createCell(e.getCol()).setCellValue(m.get(e.getFieldName())==null?"":m.get(e.getFieldName()).toString());//商品名称
                    }
                }
            }
            old=ENTRYID;

            for (int j = 0; j <row.getLastCellNum() ; j++) {
                Cell cell = row.getCell(j);
                if(null!=cell){
                    cell.setCellStyle(cell_Style);
                }
                //
            }
            //每当行数达到设置的值就刷新数据到硬盘,以清理内存
            if (i % rowaccess == 0) {
                sheet.flushRows();
            }

        }
    }


    public List<ExportHeads> getExportRalation(String ieFlag, HttpServletRequest request) throws IOException, DocumentException {
        String projectPath = request.getSession().getServletContext().getRealPath("");
//        File _relation_template = new File(projectPath + "/platform/downBgd/dechead_down_excel_template.xml");
        InputStream model_template = this.getClass().getClassLoader().getResourceAsStream("template/dechead_down_excel_template.xml");
        Map<String, ExportHeads> map = new HashMap<String, ExportHeads>();
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(model_template);
        Element root = doc.getRootElement();
        List<ExportHeads> list = new ArrayList<>();
        for (Object obj : root.elements()) {

            Element element = (Element) obj;
            String str = element.attributeValue("name");
            if(ieFlag.equals(str)){
                hbCol = Integer.parseInt(element.attribute("hbCol").getText());
                for (Object object : element.elements()) {
                    ExportHeads model = new ExportHeads();
                    element = (Element) object;
                    model.setCol(Integer.parseInt(element.attribute("col").getText()));
                    model.setFieldName(element.attribute("fieldName").getText());
                    model.setName(element.attribute("name").getText());
                    list.add(model);
                }
            }
        }
        return list;
    }

    public CellStyle createHeaderStyle(Workbook wb){
        CellStyle topStyle = (CellStyle) wb.createCellStyle();// 创建标题样式
        topStyle.setVerticalAlignment(topStyle.getVerticalAlignmentEnum().CENTER);    //设置垂直居中
        topStyle.setAlignment(HorizontalAlignment.CENTER);   //设置水平居中
        topStyle.setWrapText(true);
        Font topFont = (Font) wb.createFont(); //创建字体样式
//        topFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗
        topFont.setBold(true); // 字体加粗
        topFont.setFontName("宋体");  //设置字体类型
        topFont.setFontHeightInPoints((short) 16);    //设置字体大小
        topStyle.setFont(topFont);    //为标题样式设置字体样式
        return topStyle;
    }

    public CellStyle createTitleStyle(Workbook wb){
        CellStyle headerStyle = (CellStyle) wb.createCellStyle();// 创建标题样式
//        HSSFCellStyle headerStyle = (HSSFCellStyle) wb.createCellStyle();
        headerStyle.setVerticalAlignment(topStyle.getVerticalAlignmentEnum().CENTER);    //设置垂直居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);   //设置水平居中
        headerStyle.setWrapText(true);
        Font headerFont = (Font) wb.createFont(); //创建字体样式
        headerFont.setBold(true); // 字体加粗
        headerFont.setFontName("宋体");  //设置字体类型
        headerFont.setFontHeightInPoints((short) 10);    //设置字体大小
        headerStyle.setFont(headerFont);    //为标题样式设置字体样式
        headerStyle.setBorderTop(BorderStyle.THIN);// 上边框
        headerStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        headerStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        headerStyle.setBorderRight(BorderStyle.THIN);// 右边框
        return headerStyle;
    }

    public CellStyle createDateStyle(Workbook wb){
        CellStyle dateStyle = (CellStyle) wb.createCellStyle();// 创建标题样式
//        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);    //设置垂直居中
//        dateStyle.setAlignment(CellStyle.ALIGN_CENTER);   //设置水平居中
        dateStyle.setVerticalAlignment(topStyle.getVerticalAlignmentEnum().CENTER);    //设置垂直居中
        dateStyle.setAlignment(HorizontalAlignment.CENTER);   //设置水平居中
        dateStyle.setWrapText(true);
        Font dateFont = (Font) wb.createFont(); //创建字体样式
//        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗
        dateFont.setBold(true);
        dateFont.setFontName("宋体");  //设置字体类型
        dateFont.setFontHeightInPoints((short) 12);    //设置字体大小
        dateStyle.setFont(dateFont);    //为标题样式设置字体样式
        return dateStyle;
    }

    public CellStyle createCellStyle(Workbook wb){
        CellStyle cell_Style = (CellStyle) wb.createCellStyle();// 设置字体样式
//        cell_Style.setAlignment(CellStyle.ALIGN_CENTER);
//        cell_Style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直对齐居中
        cell_Style.setVerticalAlignment(topStyle.getVerticalAlignmentEnum().CENTER);    //设置垂直居中
        cell_Style.setAlignment(HorizontalAlignment.CENTER);   //设置水平居中
        cell_Style.setWrapText(true);
        Font cell_Font = (Font) wb.createFont();
        cell_Font.setFontName("宋体");
        cell_Font.setFontHeightInPoints((short) 10);
        cell_Style.setFont(cell_Font);
        cell_Style.setBorderBottom(BorderStyle.THIN); // 下边框
        cell_Style.setBorderLeft(BorderStyle.THIN);// 左边框
        cell_Style.setBorderTop(BorderStyle.THIN);// 上边框
        cell_Style.setBorderRight(BorderStyle.THIN);// 右边框
        return cell_Style;
    }


}
