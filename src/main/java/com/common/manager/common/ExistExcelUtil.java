package com.common.manager.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * 对已经存在的excel文件进行编辑，修改
 */
public class ExistExcelUtil {

    private Workbook source = null;
    private String fileName="";

    /**
     * 判断文件格式,得到workbook
     *
     * @param fileName
     * @return
     */
    public void getWorkbook(String fileName){
        try{
            InputStream in = new FileInputStream(fileName);
            //Workbook book = null;
            String filetype = fileName.substring(fileName.lastIndexOf("."));
            this.fileName=fileName;
            if (".xls".equals(filetype)) {
                source = new HSSFWorkbook(in);
            } else if (".xlsx".equals(filetype)) {
                source = new XSSFWorkbook(in);
            } else {
                System.out.println("请上传excel文件！");
            }
            //公式自动重新计算
            source.setForceFormulaRecalculation(true);
            in.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 修改单独单元格的方法，数据类型为String
     * @param sheetName
     * @param rowNum
     * @param columnNum
     * @param value
     */
    public void writeExcel(String sheetName, int rowNum, int columnNum, String value) {
        //设置sheetName
        Sheet sheet = source.getSheet(sheetName);
        Cell cell = sheet.getRow(rowNum).getCell(columnNum);
        cell.setCellValue(value);
    }

    /**
     * 修改单独单元格的方法，数据类型为Double
     * @param sheetName
     * @param rowNum
     * @param columnNum
     * @param value
     */
    public void writeExcel(String sheetName, int rowNum, int columnNum, Double value) {
        //设置sheetName
        Sheet sheet = source.getSheet(sheetName);
        Cell cell = sheet.getRow(rowNum).getCell(columnNum);
        cell.setCellValue(value);
    }

    /**
     * 编辑完成之后，关闭文件流
     */
    public void closeWorkbook() {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            source.write(out);
            source.close();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
