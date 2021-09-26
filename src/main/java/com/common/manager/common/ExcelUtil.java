package com.common.manager.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.portable.OutputStream;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * excel工具类
 */
public class ExcelUtil {
    /**
     * 课程excel
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public static List getListByExcel(InputStream in, String fileName) throws Exception {

        List list = new ArrayList<>();

        // 创建excel工作簿
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }

        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //循环所有sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {

            sheet = work.getSheetAt(i);
            if(sheet == null) {
                continue;
            }


            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                // 滤过第一行标题
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<>();
                //按列取值
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    //cell.setCellType(CellType.STRING);
                    //double s1 = cell.getNumericCellValue();
                    //Date date = HSSFDateUtil.getJavaDate(s1);
                    String word=cell.getStringCellValue();
                    li.add(word);
                }
                list.add(li);
            }
        }
        work.close();
        return list;
    }

    /**
     * 判断文件格式
     * @param in
     * @param fileName
     * @return
     */
    private static Workbook getWorkbook(InputStream in, String fileName) throws Exception {

        Workbook book = null;
        String filetype = fileName.substring(fileName.lastIndexOf("."));

        if(".xls".equals(filetype)) {
            book = new HSSFWorkbook(in);
        } else if (".xlsx".equals(filetype)) {
            book = new XSSFWorkbook(in);
        } else {
            throw new Exception("请上传excel文件！");
        }

        return book;
    }


    /**
     * 导出到excel 表格
     *
     * @param response 返回
     * @param list     数据
     * @param headers  表头
     * @throws Exception
     */
    public static void exportForSum(HttpServletResponse response, List<List<List<String>>> list, List<List<String>> headers, List<String> sheetName) throws Exception {
        String fileName = "查询结果" + new Date().toLocaleString() + ".xlsx";//设置要导出的文件的名字
        Workbook workbook=excelWrite(list,headers,sheetName);
        if(response!=null){
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", "FileName");
            response.setHeader("FileName", URLEncoder.encode(fileName, "utf-8"));
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=\"" + URLEncoder.encode(fileName) + "\"");
            response.flushBuffer();
            workbook.write(response.getOutputStream());
            System.out.println("返回完成");
        }

    }
    /**
     * 导出到excel 表格
     *
     * @param fileName 生成文件名
     * @param list     数据
     * @param headers  表头
     * @throws Exception
     */
    public static void exportForSum(String fileName, List<List<List<String>>> list, List<List<String>> headers, List<String> sheetName) throws Exception {
        Workbook workbook=excelWrite(list,headers,sheetName);
        FileOutputStream out=new FileOutputStream(fileName);
        workbook.write(out);
        workbook.close();
        out.close();
    }

    /**
     * 导出到excel 表格 返回inputStream
     *
     * @param list     数据
     * @param headers  表头
     * @throws Exception
     */
    public static InputStream exportForIn( List<List<List<String>>> list, List<List<String>> headers, List<String> sheetName) throws Exception {
        Workbook workbook=excelWrite(list,headers,sheetName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //FileOutputStream outs =new FileOutputStream("test.xlsx");
        workbook.write(out);
        //workbook.write(outs);
        workbook.close();
        //outs.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * 创建excel并写入，返回workbook
     * @param list 主体数据
     * @param headers 表头
     * @param sheetName sheet名称
     * @return
     */
    private static Workbook excelWrite( List<List<List<String>>> list, List<List<String>> headers, List<String> sheetName){
        // 创建工作薄 xsl
        //HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作薄 xlsx
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表 xsl
        //HSSFSheet sheet = workbook.createSheet("信息表");
        // 创建工作表 xlsx
        for (int index = 0; index < list.size(); index++) {
            //设置sheetName
            Sheet sheet = workbook.createSheet(sheetName.get(index));

            //新增数据行，并且设置单元格数据
            int rowNum = 1;
            Row rows = sheet.createRow(0);
            //取出当前页的表头
            List<String> header=headers.get(index);
            //在excel表中添加当前页表头
            for (int i = 0; i < header.size(); i++) {
                // 向工作表中添加数据
                rows.createCell(i).setCellValue(header.get(i));
            }
            //取出当前面的数据
            List<List<String>> sheetList = list.get(index);
            //在表中存放查询到的数据放入对应的列
            for (int k = 0; k < sheetList.size(); k++) {
                //HSSFRow row1 = sheet.createRow(rowNum);
                //增加新的一行
                rows = sheet.createRow(rowNum);
                //新的一行每列插入数据
                for(int j=0 ;j<sheetList.get(k).size();j++){
                    rows.createCell(j).setCellValue(sheetList.get(k).get(j));
                }
                rowNum++;
            }
        }
        return workbook;
    }

}
