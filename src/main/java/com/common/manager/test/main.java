package com.common.manager.test;

import com.common.manager.common.ExistExcelUtil;

public class main {
    public static void main(String[] args) {
        ExistExcelUtil existExcelUtil=new ExistExcelUtil();
        existExcelUtil.getWorkbook("test.xlsx");
        existExcelUtil.writeExcel("test",1,1,5565.9568);
        existExcelUtil.closeWorkbook();
    }
}
