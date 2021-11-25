package com.common.manager.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PageUtil {

    public static  <T> Page<T> buildPage(IPage page, Class<T> clazz){
        Page<T> pageResult = new Page<>();
        BeanUtils.copyProperties(page,pageResult);
        pageResult.setRecords((List<T>) page.getRecords().stream().map(item -> {
            T t ;
            try {
                t= clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            BeanUtils.copyProperties(item,t);
            return t;
        }).collect(Collectors.toList()));
        return pageResult;
    }
    public static <T>List<T> do2info(List<?> source,Class<T> clazz)  {
        if(source==null) return null;
        List<T> results =new ArrayList<>();
        for(int i=0;i<source.size();i++){
            try{
                T t=clazz.newInstance();
                if(source.get(i)!=null){
                    BeanUtils.copyProperties(source.get(i),t);
                    results.add(t);
                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
        return results;
    }

    public static List<List<String>> do2String(List<?> source)  {
        if(source==null) return null;
        List<List<String>> results =new ArrayList<>();
        for(int i=0;i<source.size();i++){
            try{

                if(source.get(i)!=null){
                    List<String> row=new ArrayList<>();
                    //得到class
                    Class cls = source.get(i).getClass();
                    //得到所有属性
                    Field[] fields = cls.getDeclaredFields();
                    for (int k=0;k<fields.length;k++){//遍历
                        try {
                            //得到属性
                            Field field = fields[k];
                            //打开私有访问
                            field.setAccessible(true);
                            //获取属性
                            String name = field.getName();
                            //获取属性值
                            Object value = field.get(source.get(i))==null?"":field.get(source.get(i));
                            //一个个赋值
                            row.add(String.valueOf(value));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    results.add(row);
                }

            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
        return results;
    }
}
