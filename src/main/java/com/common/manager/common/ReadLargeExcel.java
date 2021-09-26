package com.common.manager.common;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract public class ReadLargeExcel extends DefaultHandler {
    private SharedStringsTable sst;
    private String lastContents;
    private int sheetIndex = -1;
    public int curRow = 0;
    public int appendRowNum=0;

    static boolean isLarge=false;

    //抽象方法，子类覆盖，自动调用
    abstract public void optRow(int sheetIndex, int curRow, List<String> rowList) ;
    //有效数据矩形区域,A1:Y2
    private String dimension;

    //根据dimension得出每行的数据长度
    private int longest;

    //上个有内容的单元格id，判断空单元格
    private String lastRowid;

    //行数据保存
    private List<String> currentRow;

    //单元格内容是SST 的索引
    private boolean isSSTIndex=false;


    /**
     * 读取第一个工作簿的入口方法
     * @param fileName
     */
    public void readOneSheet(String fileName,String sheetNum) throws Exception {
        OPCPackage pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        InputStream sheet = r.getSheet(sheetNum);//r.getSheet("rId1");

        InputSource sheetSource = new InputSource(sheet);
        parser.parse(sheetSource);
        System.out.println(fileName +"<===>完成！");
        sheet.close();
        pkg.close();
    }




    /**
     * 读取所有工作簿的入口方法
     * @param fileName
     * @throws Exception
     */
    public void process(String fileName) throws Exception {
        OPCPackage pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            System.out.println(fileName +"<===>完成！");
            sheet.close();

        }
        pkg.close();
    }

    /**
     * 读取所有工作簿的入口方法
     * @param fileName
     * @throws Exception
     */
    public void process(String fileName,String sheetName) throws Exception {
        OPCPackage pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        XSSFReader.SheetIterator sheetiterator = (XSSFReader.SheetIterator) sheets;
        while (sheetiterator.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheetiterator.next();
            if(!sheetiterator.getSheetName().equals(sheetName)){
                continue;
            }
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            System.out.println(fileName +"<===>excel完成！");
            sheet.close();
        }
        pkg.close();
    }
    /**
     * 读取所有工作簿的入口方法
     * @param in inputStream
     * @throws Exception
     */
    public void process(InputStream in,String sheetName) throws Exception {
        OPCPackage pkg = OPCPackage.open(in);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        XSSFReader.SheetIterator sheetiterator = (XSSFReader.SheetIterator) sheets;
        while (sheetiterator.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheetiterator.next();
            if(!sheetiterator.getSheetName().equals(sheetName)){
                continue;
            }
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            System.out.println(sheetName +"<===>excel完成！");
            sheet.close();
        }
        pkg.close();
        in.close();
    }

   // /**
   //  * 该方法自动被调用，每读一行调用一次，在方法中写自己的业务逻辑即可
   //  * @param sheetIndex 工作簿序号
   //  * @param curRow 处理到第几行
   //  * @param rowList 当前数据行的数据集合
   //  */
    /*public void optRow(int sheetIndex, int curRow, List<String> rowList) {
        try{
            isLarge=true;
            Boolean write=false;
            if(curRow==0 && appendRowNum!=0 ){

            }else{
                //appendTxt(outputFileName,rowList);
                List<List<String>> list=new ArrayList<>();
                list.add(rowList);
                appendExcel.writeExcel(list);
            }
            appendRowNum++;
        }catch (Exception e){

        }

       *//* String temp = "";
        for(String str : rowList) {
            temp += str + "_";
        }
        System.out.println(temp);*//*
    }*/


    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory
                .createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //记录数据最后的列数
        if (qName.equals("dimension")){
            //记录数据最后的列数
            dimension = attributes.getValue("ref");
            longest = covertRowIdtoInt(dimension.substring(dimension.indexOf(":")+1) );
        }
        //行开始
        if (qName.equals("row")) {
            currentRow = new ArrayList<>();
        }
        if (qName.equals("c")) {
            String rowId = attributes.getValue("r");
            //空单元判断，添加空字符到list
            if (lastRowid!=null)
            {
                int gap = covertRowIdtoInt(rowId)-covertRowIdtoInt(lastRowid);
                for(int i=0;i<gap-1;i++)
                {
                    currentRow.add("");
                }
            }else{
                //第一个单元格可能不是在第一列
                if (!"A1".equals(rowId))
                {
                    for(int i=0;i<covertRowIdtoInt(rowId)-1;i++)
                    {
                        currentRow.add("");
                    }
                }
            }
            //记录最后数据的列index
            lastRowid = rowId;
            //此格有数据
            if (qName.equals("c")) {
                //判断单元格的值是SST 的索引，不能直接characters方法取值
                String cellType = attributes.getValue("t");
                if (cellType != null && (cellType.equals("s"))) {
                    isSSTIndex = true;
                } else {
                    isSSTIndex = false;
                }
            }
            // 置空
            lastContents = "";
        }

    }



    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //行结束,存储一行数据
        if (qName.equals("row")) {
            if (lastRowid!=null) {
                //判断最后一个单元格是否在最后，补齐列数
                if (covertRowIdtoInt(lastRowid) < longest) {
                    for (int i = 0; i < longest - covertRowIdtoInt(lastRowid); i++) {
                        currentRow.add("");
                    }
                }
            }
            //回调函数到自定义类处理
            optRow(sheetIndex,curRow,currentRow);
            //行数增加、重置列数index
            curRow++;
            appendRowNum++;
            lastRowid=null;
        }
        //清空公式
        if(qName.equals("f")){lastContents="";}
        //单元格内容标签结束，characters方法会被调用处理内容
        if (qName.equals("c")) {
            //单元格的值是SST 的索引
            if (isSSTIndex){
                try {
                    int idx = Integer.parseInt(lastContents);
                    //if(idx<sst.getUniqueCount()){
                        lastContents = sst.getItemAt(idx).getString();//new XSSFRichTextString(sst.getItemAt(idx)).toString();
                        currentRow.add(lastContents);
                    //}else{
                        //System.out.println(idx+"<==>" + curRow);
                    //}
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }else{
                //单元格的值不是SST 的索引，直接加入数据
                currentRow.add(lastContents);
            }

        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }
    /**
     * 列号转数字   AB7-->28 第28列
     * @param rowId
     * @return
     */
    public static int covertRowIdtoInt(String rowId){
        int firstDigit = -1;
        for (int c = 0; c < rowId.length(); ++c) {
            if (Character.isDigit(rowId.charAt(c))) {
                firstDigit = c;
                break;
            }
        }
        //AB7-->AB
        //AB是列号, 7是行号
        String newRowId = rowId.substring(0,firstDigit);
        int num = 0;
        int result = 0;
        int length = newRowId.length();
        for(int i = 0; i < length; i++) {
            //先取最低位，B
            char ch = newRowId.charAt(length - i - 1);
            //B表示的十进制2，ascii码相减，以A的ascii码为基准，A表示1，B表示2
            num = (int)(ch - 'A' + 1) ;
            //列号转换相当于26进制数转10进制
            num *= Math.pow(26, i);
            result += num;
        }
        return result;

    }

    /**
     * 初始化参数
     */
    public void initi(){
        curRow = 0;
        appendRowNum=0;
    }

    public void nextFile(){
        curRow=0;
        //appendRowNum++;
    }
}
