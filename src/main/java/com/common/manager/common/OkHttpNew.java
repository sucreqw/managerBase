package com.common.manager.common;


import okhttp3.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpNew {

    private String header;
    private String body;
    private byte[] bodyByte;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getBodyByte() {
        return bodyByte;
    }

    public void setBodyByte(byte[] bodyByte) {
        this.bodyByte = bodyByte;
    }

    /**
     * 重载post请求，去掉报关参数。
     *
     * @param url
     * @param body
     * @return
     */
    public String goPost(String url, HashMap<String, String> body) {
        return goPost(url, null, body);
    }

    /**
     * post 请求，报头可为空。
     *
     * @param url    连接地址 可带上端口号。
     * @param header 报头，可为空
     * @param body   key,value形式的body
     * @return
     */
    public String goPost(String url, HashMap<String, String> header, HashMap<String, String> body) {
        OkHttpClient.Builder client =new OkHttpClient().newBuilder().readTimeout(800,TimeUnit.SECONDS).followRedirects(false);
        // client = new OkHttpClient();
        FormBody.Builder requestBody = new FormBody.Builder();

        //加入body
        if (body != null && body.size() != 0) {
            for (String bodys : body.keySet()) {
                requestBody.add(bodys, body.get(bodys));
            }
        }
        Request.Builder request = new Request.Builder();

        //加入报头
        if (header != null && header.size() != 0) {
            for (String head : header.keySet()) {
                request.addHeader(head, header.get(head));
            }
        }
        request.url(url);
        request.post(requestBody.build());
        //request.build();

        try (
                Response response = client.build().newCall(request.build()).execute()
        ) {
            this.header=response.headers().toString();
            this.body=response.body().toString();
            return response.body().string();
        } catch (Exception e) {
            System.out.println("okhttp出错！" + e.getMessage());
        }


        return null;
    }
    /**
     * post 请求，报头可为空。
     *
     * @param url    连接地址 可带上端口号。
     * @param header 报头，可为空
     * @param body   {} json 格式的body
     * @return
     */
    public String goPost(String url, HashMap<String, String> header, String body) {
        OkHttpClient.Builder client =new OkHttpClient().newBuilder().readTimeout(800,TimeUnit.SECONDS).followRedirects(false);
        // client = new OkHttpClient();
        MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON,body);
        Request.Builder request = new Request.Builder();

        //加入报头
        if (header != null && header.size() != 0) {
            for (String head : header.keySet()) {
                request.addHeader(head, header.get(head));
            }
        }
        request.url(url);
        request.post(requestBody);
        //request.build();

        try (
                Response response = client.build().newCall(request.build()).execute()
        ) {
            this.header=response.headers().toString();
            this.body=response.body().toString();
            return response.body().string();
        } catch (Exception e) {
            System.out.println("okhttp出错！" + e.getMessage());
        }


        return null;
    }

    /**
     * post 请求，报头可为空。
     *
     * @param url    连接地址 可带上端口号。
     * @param header 报头，可为空
     * @param body   要上传的文件
     * @return
     */
    public String goPost(String url, HashMap<String, String> header, File body) {
        OkHttpClient.Builder client =new OkHttpClient().newBuilder().readTimeout(800,TimeUnit.SECONDS).followRedirects(false);
        // client = new OkHttpClient();
        String imageType = "multipart/form-data";
        //File file = new File(imgUrl);imgUrl为图片位置
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), body);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "head_image", fileBody)
                .addFormDataPart("imagetype", imageType)
                .build();

        //MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //RequestBody requestBody = RequestBody.create(JSON,body);
        Request.Builder request = new Request.Builder();

        //加入报头
        if (header != null && header.size() != 0) {
            for (String head : header.keySet()) {
                request.addHeader(head, header.get(head));
            }
        }
        request.url(url);
        request.post(requestBody);
        //request.build();

        try (
                Response response = client.build().newCall(request.build()).execute()
        ) {
            this.header=response.headers().toString();
            this.body=response.body().toString();
            return response.body().string();
        } catch (Exception e) {
            System.out.println("okhttp出错！" + e.getMessage());
        }


        return null;
    }

    /**
     * 重载get请求，无报头参数。
     *
     * @param url
     * @return
     */
    public String goGet(String url) {
        return goGet(url, null);
    }

    /**
     * get请求，参数可以直接定义在url里。
     *
     * @param url
     * @param header 自定义报头，可为空。
     * @return
     */
    public String goGet(String url, HashMap<String, String> header) {
        OkHttpClient.Builder client =new OkHttpClient().newBuilder().readTimeout(800,TimeUnit.SECONDS).followRedirects(false);
        //OkHttpClient client = new OkHttpClient();

        Request.Builder request = new Request.Builder();

        //加入报头
        if (header != null && header.size() != 0) {
            for (String head : header.keySet()) {
                request.addHeader(head, header.get(head));
            }
        }
        request.url(url);
        //request.build();

        try (
                Response response = client.build().newCall(request.build()).execute()
        ) {
            this.header=response.headers().toString();
            this.body=response.body().toString();
            return response.body().string();
        } catch (Exception e) {
            System.out.println("okhttp出错！" + e.getMessage());
        }


        return null;
    }

    /**
     * 以字节形式返回数据，不用定义header,返回数据不包括报头
     * @param url
     * @return
     */
    public byte[] goGetByte(String url){
        return goGetByte(url,null);
    }
    /**
     * 以字节形式返回数据，不用定义header,返回数据不包括报头
     * @param url
     * @return
     */
    public byte[] goGetByteWithOutHeader(String url){
        return goGetByte(url,null);
    }
    /**
     * 以字节形式返回数据，定义header ,返回数据包括报头
     * @param url
     * @param header
     * @return
     */
    public byte[] goGetByte(String url,HashMap<String, String> header){
        //List<Object> list=new ArrayList<>();
        //OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder client =new OkHttpClient().newBuilder().readTimeout(800,TimeUnit.SECONDS).followRedirects(false);
        Request.Builder request = new Request.Builder();

        //加入报头
        if (header != null && header.size() != 0) {
            for (String head : header.keySet()) {
                request.addHeader(head, header.get(head));
            }
        }
        request.url(url);
        //request.build();

        try (
                Response response = client.build().newCall(request.build()).execute()
        ) {
            //System.out.println(response.headers());
            this.header=response.headers().toString();
            this.bodyByte=response.body().bytes();
            //list.add(response.headers().toString());
            //list.add(response.body().bytes());
            return response.body().bytes();
        } catch (Exception e) {
            System.out.println("okhttp出错！" + e.getMessage());
        }


        return null;
    }

    /**
     * 以字节形式返回数据，不用定义header,返回数据不包括报头
     * @param url
     * @return
     */
    public byte[] goPostByteWithOutHeader(String url,HashMap<String,String> body){
        return goPostByte(url,null,body);
    }
    /**
     * 以字节形式返回数据，定义header ,返回数据包括报头
     * @param url
     * @param header
     * @return
     */
    public byte[] goPostByte(String url,HashMap<String, String> header,HashMap<String,String> body){
        OkHttpClient.Builder client =new OkHttpClient().newBuilder().readTimeout(800,TimeUnit.SECONDS).followRedirects(false);
        //List<Object> list=new ArrayList<>();
        //OkHttpClient client = new OkHttpClient(builder);

        Request.Builder request = new Request.Builder();
        //加入body
        FormBody.Builder requestBody = new FormBody.Builder();
        if (body != null && body.size() != 0) {
            for (String bodys : body.keySet()) {
                requestBody.add(bodys, body.get(bodys));
            }
        }
        //加入报头
        if (header != null && header.size() != 0) {
            for (String head : header.keySet()) {
                request.addHeader(head, header.get(head));
            }
        }
        request.url(url);
        //request.build();
        request.post(requestBody.build());

        try (
                //Response response = client.newCall(request.build()).execute()
                Response response=client.build().newCall(request.build()).execute()
        ) {
            //System.out.println(response.headers());
            this.header=response.headers().toString();
            this.bodyByte=response.body().bytes();
            //list.add(response.headers().toString());
            //list.add(response.body().bytes());
            return response.body().bytes();
        } catch (Exception e) {
            System.out.println("okhttp出错！" + e.getMessage());
        }
        return null;
    }
}
