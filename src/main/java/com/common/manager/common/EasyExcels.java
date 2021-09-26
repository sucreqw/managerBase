package com.common.manager.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class EasyExcels {
    /**
     * 最简单的读
     * <p>
     * 1. 创建excel对应的实体对象 参照
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照
     * <p>
     * 3. 直接读即可
     */
    public void simpleRead(String fileName, AnalysisEventListener<Map<Integer,String>> listener) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        //String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        //EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // 写法2：
        //fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName,listener).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            readSheet.getHead();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    /**
     * 指定列的下标或者列名
     *
     * <p>
     * 1. 创建excel对应的实体对象,并使用{@link ExcelProperty}注解. 参照{@link IndexOrNameData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link IndexOrNameDataListener}
     * <p>
     * 3. 直接读即可
     */

    /*public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();
    }*/

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link }
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link }
     * <p>
     * 3. 直接读即可
     */

    public void repeatedRead(String fileName,Integer sheetIndex) {
        //String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        //EasyExcel.read(fileName, String.class, new EasyExcelListener()).doReadAll();

        // 读取部分sheet
        //fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName).build();

            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            //ReadSheet readSheet1 =
            //EasyExcel.readSheet(sheetIndex).head(RowData.class).registerReadListener(new EasyExcelListener()).build();
            // ReadSheet readSheet2 =
            //EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            // excelReader.read(readSheet1);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    /**
     * 不创建对象的读
     */

    public void noModelRead( String fileName,Integer sheetIndex,AnalysisEventListener<Map<Integer,String>> listener) {
        //String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, listener).sheet(sheetIndex).doRead();
    }

    /**
     * 不创建对象的读 用sheet名称
     */

    public void noModelRead( String fileName,String sheetName,AnalysisEventListener<Map<Integer,String>> listener) {
        //String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, listener).sheet(null,sheetName).doRead();
    }
    /**
     * 不创建对象的读全部
     */

    public void noModelRead( String fileName,AnalysisEventListener<Map<Integer,String>> listener) {
        //String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, listener).doReadAll();

    }

    /**
     * 网络数据读取
     *
     */
    public void webRead(byte[] source,String sheetName,AnalysisEventListener<Map<Integer,String>> listener){
        InputStream in = new ByteArrayInputStream(source);
        EasyExcel.read(in,listener).sheet(null,sheetName).doRead();
    }
    /**
     * 网络数据读取
     *
     */
    public void webRead(InputStream source,String sheetName,AnalysisEventListener<Map<Integer,String>> listener){
        //InputStream in = new ByteArrayInputStream(source);
        EasyExcel.read(source,listener).sheet(null,sheetName).doRead();
    }
    /**
     * 不创建对象的写
     */

    public void noModelWrite(String fileName, List<List<String>> head, List<List<Object>> dataList) {
        // 写法1
        //String fileName = TestFileUtil.getPath() + "noModelWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head).sheet("sheet1").doWrite(dataList);
    }
}
