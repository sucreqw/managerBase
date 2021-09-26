package com.common.manager.common;

import org.apache.commons.lang.time.DateFormatUtils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

public class LargeExcelUtil {

    /**
     *
     * 大数据导出，加快速度 占用内存少
     * @param fileName
     * @param list
     * @param headers
     * @param sheetName
     * @throws Exception
     */
    public static void exportForSum(String fileName, List<List<List<String>>> list, List<List<String>> headers, List<String> sheetName) throws Exception {
        // 创建一个工作簿
        Workbook workbook = new SXSSFWorkbook();
        //循环所有数据
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

        // 创建一个文件
        File file = new File(fileName);
        try {
            file.createNewFile();
            // 打开文件流
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
