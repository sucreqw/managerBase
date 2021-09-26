package com.common.manager.common;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class MergeExcelUtil {
    private Workbook source = null;
    private String fileName="";
    HashMap<String,Integer> curRow=new HashMap<>();//记录当前行
    /**
     * 创建一个新的excel
     */
    public void initiate(){
        // 创建一个工作簿
        source = new SXSSFWorkbook();
    }

    /**
     * 追加数据到已有的excel里
     * @param sheetName
     * @param data
     */
    public void addList(String sheetName, List<List<String>> data){
        Sheet sheet = source.getSheet(sheetName);
        if(sheet==null){
            sheet=source.createSheet(sheetName);
            curRow.put(sheetName,0);//记得当前行
        }
        for(int i =0; i<data.size();i++){
            Row rows = sheet.createRow(curRow.get(sheetName));
            for(int k =0; k<data.get(i).size();k++){
                rows.createCell(k).setCellValue(data.get(i).get(k));
            }
            //记得当前行
            curRow.put(sheetName,curRow.get(sheetName)+1);
        }
    }

    /**
     * 保存excel到指定目录
     * @param fileName
     */
    public void saveExcel(String fileName){
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            source.write(out);
            source.close();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 取当前excel的inputStream
     * @return
     */
    public InputStream getInputStream(){
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            source.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 关闭当前excel
     * @return
     */
    public void close(){
        try{
            source.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
