package com.common.manager.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {
    /**
     * 取当前时返回10位
     *
     * @return 当前时间毫秒
     */
    public static String getTimeB() {
        long t = System.currentTimeMillis();
        return String.valueOf(t).substring(0, 10);
    }
    /**
     * 返回当前时间，按指定格式
     * @param format
     */
    public static String getNow(String format){
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(format);

        try {
            //方法一
            Date date = new Date();
            tsStr = sdf.format(date);
			 /*//方法二
             tsStr = ts.toString();
             System.out.println(tsStr);*/
        } catch (Exception es) {

            System.out.println("timestamp出错！" + es.getMessage());
        }
        return tsStr;
    }
    /**
     * 返回今天或者明天 格式
     * @param diff 0 为今天，1为明天 yyyy-MM-dd
     * @return
     */
    public static String getDay(int diff){
        Long n=System.currentTimeMillis();
        n=n+(diff*(1000*60*60*24));
        Date date=new Date(n);//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");//设置当前时间的格式，为年-月-日
        return dateFormat.format(date);
    }
    /**
     * 把传进来的时间加减 天数。
     * @param source 源时间
     * @param sourceFormat 源时间格式
     * @param returnFormat 返回格式
     * @param diff 加减天数
     * @return
     */
    public static String dateDiff(String source,String sourceFormat,String returnFormat,int diff){
        String tsStr = "";
        Calendar calendar   = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat(sourceFormat);
        DateFormat sdfReturn = new SimpleDateFormat(returnFormat);
        try{
            Date date = sdf.parse(source);
            calendar.setTime(date);
            //calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
            // calendar.add(calendar.DAY_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
            calendar.add(calendar.DATE,diff);//把日期往后增加一天.整数往后推,负数往前移动
            //calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
            date=calendar.getTime();   //这个时间就是日期往后推一天的结果
            tsStr = sdfReturn.format(date);
        }catch (Exception e){
            System.out.println("timestamp出错！" + e.getMessage());
        }
        return  tsStr;
    }
    /**
     * 根据指定格式转换时间并返回
     * @param source
     * @param sourceFormat
     * @param returnFormat
     * @return
     */
    public static String formatDateStr (String source,String sourceFormat,String returnFormat){
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(sourceFormat);
        DateFormat sdfReturn = new SimpleDateFormat(returnFormat);
        try {
            //方法一
            Date date = sdf.parse(source);
            tsStr = sdfReturn.format(date);
			 /*//方法二
             tsStr = ts.toString();
             System.out.println(tsStr);*/
        } catch (Exception es) {

            System.out.println("timestamp出错！" + es.getMessage());
        }
        return tsStr;
    }
    /**
     * 对比两个日期相差的天数，指定格式为yyyy-MM-dd
     * @param source
     * @param target
     * @return
     */
    public static Integer dateCompare(String source,String target){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date dateSource=sdf.parse(source);
            Date dateTarget = sdf2.parse(target);
            Long retSource= dateSource.getTime();
            Long retTarget = dateTarget.getTime();
            Long result = (retTarget-retSource)/1000/ 60 / 60 / 24;
            return result.intValue();
        }catch (Exception e){
            System.out.println("对比时间出错：" + e.getMessage());
        }
        return null;
    }

    /**
     * 对比两个时间，如果相同返回0，大于返回1，少于返回-1
     * @param time1
     * @param time2
     * @param sourcrFormat
     * @return
     */
    public static Integer compareTime(String time1,String time2,String sourcrFormat){
        //String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(sourcrFormat);
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            Long t1=date1.getTime();
            Long t2=date2.getTime();
            if(t1==t2){
                return 0;
            }

            if(t1>t2){
                return 1;
            }
            if(t1<t2){
                return -1;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
    /**
     * 对比两个日期，返回相差的天数
     * @param time1
     * @param time2
     * @param sourcrFormat

     * @return
     */
    public static Integer compareDate(String time1,String time2,String sourcrFormat){
        //String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(sourcrFormat);
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            Long t1=date1.getTime();
            Long t2=date2.getTime();
            int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
            return Math.abs(days);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 去除小数点
     * @param source
     * @return
     */
    public static String cutPoint(String source){
        if(source==null) return "";
        int i=source.indexOf(".");
        if(i==-1){
            return source;
        }else{
            return MyUtil.midWord("a",".","a"+source);
        }
    }

    /**
     * string 转 数字 Double
     * @param source 要转换的字符
     * @return 失败返回null
     */
    public static Double string2Double(String source){
        Double ret=null;
        try{
            ret=Double.valueOf(source);
            //return ret;
        } catch (Exception e){
            ret=null;
        }finally {
            return ret;
        }

    }
}
