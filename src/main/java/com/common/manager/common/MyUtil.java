package com.common.manager.common;





import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPOutputStream;


/**
 * 常用工具?.
 *
 * @author sucre
 */
public class MyUtil {
    //	// 定义各种List
//	public static MutiList listId = new MutiList();
//	public static MutiList listVid = new MutiList();
//	public static MutiList listCookie = new MutiList();
    //计数器
    public static int counts = 0;
    public static int succcounts = 0;
    public static Random r = new Random(System.currentTimeMillis());
//	/**
//	 * @param fileName 为当前目录下的文件名,带后�?
//	 * @param list     为要加载进入的list对象
//	 * @return 成功则返回list的size,不成功则返回错误信息.
//	 */
//	public static String loadList(String fName, MutiList list) {
//
//		try {
//			// 加载文件
//			list.loadFromFile(fName);
//			return ("导入成功<==>" + String.valueOf(list.getSize()));
//		} catch (IOException e) {
//			return ("导入错误：" + e.getMessage());
//		}
//
//	}

    /**
     * aes加密方法,默认为AES/ECB/NoPadding,128位数据块,偏移量为�?.
     *
     * @param keys 加密 的密�?
     * @param data 要加密的数据
     * @return 成功返回base64格式的字符串, 不成功返回null!
     */
    public static String aesEncrypt(String keys, String data) {
        String ret = null;
        try {
            // 创建cipher对象,这是�?个单例模�?.
            Cipher c = Cipher.getInstance("AES/ECB/NoPadding");
            // 创建�?个key对象.
            SecretKeySpec key = new SecretKeySpec(keys.getBytes(), "AES");
            // 初始化为加密模式.
            c.init(Cipher.ENCRYPT_MODE, key);
            // 加密并返回byte[]
            byte[] result = c.doFinal(data.getBytes());
            // 对加密结果进行base64编码
            ret = new String(Base64.getEncoder().encode(result));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    /**
     * aes加密方法,默认为AES/ECB/PKCS5Padding,128位数据块
     *
     * @param sKey 加密 的密码?
     * @param sSrc 要加密的数据
     * @return 成功返回base64格式的字符串, 不成功返回null!
     */
    public static String EncryptAes(String sSrc, String sKey) {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        try{
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return new String( Base64.getEncoder().encode(encrypted));//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        }catch (Exception E){
            E.printStackTrace();
            System.out.println(E.getMessage());
        }
        return null;
    }
    /**
     * AES解密算法.默认AES/ECB/NoPadding,128位数据块,偏移量为�?.
     *
     * @param keys 加密时的密码
     * @param data 要解密的数据
     * @return 成功返回明文, 不成功返回null
     */
    public static String aesDecrypt(String keys, String data) {

        try {
            // 创建cipher对象,这是�?个单例模�?.
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            // 创建�?个key对象
            SecretKeySpec key = new SecretKeySpec(keys.getBytes(), "AES");
            // 使用密钥初始化，设置为解密模�?
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 执行操作,先对字符作base64解码,再解�?.
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(data));
            // byte 转string 并返�?
            return (new String(result));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * des加密方法,默认为DES/ECB/PKCS5Padding
     *
     * @param keys 加密 的密�?
     * @param data 要加密的数据
     * @return 成功返回base64格式的字符串, 不成功返回null!
     */
    public static String desEncrypt(String data, String keys) {
        try{
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(keys.substring(0,8).getBytes(), "DES");
            Cipher localCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            localCipher.init(1, localSecretKeySpec);
            return Base64.getEncoder().encodeToString(localCipher.doFinal(data.getBytes()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }
    /**
     * DES加密 CEC
     *
     * @param data 加密数据
     * @param key  密钥
     * @return 返回加密后的数据
     */
    public static String desEncrypt(String data, String key, String keyiv) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * des加密方法,默认为DES/ECB/PKCS5Padding
     *
     * @param keys 加密 的密�?
     * @param data 要加密的数据
     * @return 成功返回HAX格式的字符串, 不成功返回null!
     */
    public static String desEncryptHax(String data, String keys) {
        try{
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(keys.substring(0,8).getBytes(), "DES");
            Cipher localCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            localCipher.init(1, localSecretKeySpec);
            return toHex(localCipher.doFinal(data.getBytes()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }
    /**
     * 将字符串或文件压�?
     *
     * @param data 为要压缩的字符串 @return,成功返回byte[]数组.
     */
    public static byte[] gZip(String data) {
        // 用来接收压缩后的数据
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (
                // 创建压缩对象
                GZIPOutputStream g = new GZIPOutputStream(baos);

        ) {
            // 压缩操作
            g.write(data.getBytes());
            g.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * md5标准加密算法
     *
     * @param s 要加密的字符串
     * @return
     */
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString().toLowerCase();
    }

    /**
     * 生成指定范围内的随机�?,
     *
     * @param u 范围上限
     * @param l 范围下限
     * @return String 类型的随机数�?
     */
    public static String getRand(int u, int l) {

        return String.valueOf(((r.nextInt(u - l) + l)));
    }

    /**
     * 随机生成�?串字母数字组合的字符�?
     *
     * @param n 要生成字符的长度
     * @return 长度为n的随机字符串
     */
    public static String makeNonce(int n) {
        return makeSome(n, "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * 随机生成�?串数字组合的字符�?
     *
     * @param n 要生成字符的长度
     * @return 长度为n的随机字符串
     */
    public static String makeNumber(int n) {
        return makeSome(n, "0123456789");
    }

    private static String makeSome(int n, String key) {
        String ret = "";
        if (n != 0) {

            for (int i = 1; i <= n; i++) {
                int p = Integer.parseInt(getRand(key.length(), 0));
                ret += key.charAt(p);
            }
        }
        return ret;
    }

    /**
     * 取当前时返回13位
     *
     * @return 当前时间毫秒
     */
    public static String getTime() {
        long t = System.currentTimeMillis();
        return String.valueOf(t);
    }

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
     * 随机生成一组ip
     *
     * @return
     */
    public static String getIp() {

        return getRand(255, 0) + "." + getRand(255, 0) + "." + getRand(255, 0) + "." + getRand(255, 0);
    }

    /**
     * 取出�?有cookie数据
     *
     * @param �?有数据的http报头
     * @return http报头的所有setcookie 数据
     */
    public static String getAllCookie(String data) {
        String temp = "";
        int cookieIndex = 0;
        int endIndex = 0;
        String str2find = "Set-Cookie:";
        // 循环取出字符
        while ((cookieIndex = data.indexOf(str2find, cookieIndex)) != -1) {
            // 判断cookie是否符合规格!
            if ((endIndex = data.indexOf(";", cookieIndex)) != -1) {
                // 取出字符
                temp += data.substring(cookieIndex + str2find.length(), endIndex + 1);
                cookieIndex = endIndex;
            }
        }

        return temp;
    }

    /**
     * 追加数据到文�?!文件默认位置为当前目�?
     *
     * @param fileName 要保存的文件�?,请带上后�?
     * @param data     要追加保存的数据,默认会自动加上换行符!.
     */
    public static void outPutData(String fileName, String data) {
        if ("".equals(data)) {
            return;
        }
        // 定义文件
        File file = new File(fileName);
        try (
                // 创建文件�?
                OutputStream out = new FileOutputStream(file, true);) {
            // 写入文件!
            data += "\r\n";
            out.write(data.getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 追加数据到文�?!文件默认位置为当前目�?
     *
     * @param fileName 要保存的文件�?,请带上后�?
     * @param data     要追加保存的数据,默认会自动加上换行符!.
     */
    public static void outPutData(String fileName, byte[] data) {
        outPutData(fileName,data,true);
    }
    /**
     * 追加数据到文�?!文件默认位置为当前目�?
     *
     * @param fileName 要保存的文件�?,请带上后�?
     * @param data     要追加保存的数据,默认会自动加上换行符!.
     * @param append 是否追加模式定入
     */
    public static void outPutData(String fileName, byte[] data, boolean append) {
        if ("".equals(data)) {
            return;
        }
        // 定义文件
        File file = new File(fileName);
        try (
                // 创建文件�?
                OutputStream out = new FileOutputStream(file, append);) {
            // 写入文件!
            out.flush();
            out.write(data);
            out.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static byte[] loadByte(String fileName) {
        byte[] ret = null;
        try {
            InputStream in = new FileInputStream(fileName);

            int len = 0;
            ret = new byte[in.available()];

            while ((len = in.read(ret)) != -1) {

            }
            in.close();

        } catch (Exception e) {
            System.out.println("导入文件出错");
            e.printStackTrace();
        }


        return ret;
    }

    /**
     * 判断字符是否为空!
     *
     * @param data
     * @return 为空返回true.
     */
    public static boolean isEmpty(String data) {
        return (data == null || data.trim().length() == 0) ? true : false;
    }

    /**
     * 判断list数组是否为空
     *
     * @param  list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return (list == null || list.size() == 0) ? true : false;
    }

    /**
     * 截取字符中所有的指定字符
     *
     * @param start   要截取字符的�?始位�?
     * @param ends    要截取字符的结束位置
     * @param str2mid 源字符串
     * @return 返回start 和ends �?包含位置的字符串,�?"|"分割!
     */
    public static ArrayList<String> midWordAll(String start, String ends, String str2mid) {
        if (isEmpty(str2mid)) {
            return null;
        }
        //StringBuilder ret=new StringBuilder(str2mid.length());
        ArrayList<String> ret = new ArrayList<>();
        int i = 0;
        String[] back = null;
        while ((back = midWord(start, ends, str2mid, i)) != null) {
            i = Integer.parseInt(back[1]);
            ret.add(back[0]);
        }
        return ret;
    }

    /**
     * 截取某一段字�?
     *
     * @param start   要截取字符的�?始位�?
     * @param ends    要截取字符的结束位置
     * @param str2mid 源字符串
     * @return 返回start 和ends �?包含位置的字符串
     */
    public static String midWord(String start, String ends, String str2mid) {
        String[] ret = midWord(start, ends, str2mid, 0);
        if (ret == null) {
            return null;
        }
        return ret[0];
    }

    /**
     * 截取某一段字�?
     *
     * @param start   要截取字符的�?始位�?
     * @param ends    要截取字符的结束位置
     * @param str2mid 源字符串
     * @param starts  �?始查找的位置
     * @return 返回start 和ends �?包含位置的字符串
     */
    public static String[] midWord(String start, String ends, String str2mid, int starts) {
        int begin, last;
        if ("".equals(str2mid)) {
            return null;
        }
        begin = str2mid.indexOf(start, starts);
        // 找到字符
        if (begin != -1) {
            last = str2mid.indexOf(ends, begin + start.length());
            if (last != -1) {
                String ret = str2mid.substring(begin + start.length(), last);
                starts = last+ends.length();
                return new String[]{ret, String.valueOf(last+ends.length())};
            }
        }
        return null;
    }

    /**
     * 往后面截取字符串 例 12345678
     * start =8 ends =1 结果为234567
     * @param start 要截取 最后出现的字符
     * @param ends 要截取 最开始出现的字符
     * @param str2mid 源数据
     * @return
     */
    public static String midWordRev(String start, String ends, String str2mid) {
        String[] ret = midWordRev(start, ends, str2mid, 0);
        if (ret == null) {
            return null;
        }
        return ret[0];
    }

    /**
     * 往后面截取字符串 例 12345678
     * start =8 ends =1 结果为234567
     * @param start 要截取 最后出现的字符
     * @param ends 要截取 最开始出现的字符
     * @param str2mid 源数据
     * @param starts 开始位置
     * @return
     */
    public static String[] midWordRev(String start, String ends, String str2mid, int starts){
        int begin, last;
        if ("".equals(str2mid)) {
            return null;
        }
        begin = str2mid.indexOf(start, starts);
        // 找到字符
        if (begin != -1) {
            last = str2mid.lastIndexOf(ends, begin + start.length());
            if (last != -1) {
                String ret = str2mid.substring(last+ends.length(), begin);
                starts = begin-start.length();
                return new String[]{ret, String.valueOf(starts)};
            }
        }
        return null;
    }
    /**
     * @param dataStr 要转换的字符例如\u64cd\u4f5c\u6210\u529f
     * @return 返回明名
     */
    public static String decodeUnicode(String dataStr) {

        final StringBuffer ret = new StringBuffer();
        if (!isEmpty(dataStr) && dataStr.indexOf("\\u") != -1) {
            String[] buffer = dataStr.split("(\\\\u)");
            String temp = buffer[0];
            ret.append(temp);
            for (int i = 1; i < buffer.length; i++) {
                temp = buffer[i];
                if (!isEmpty(temp)) {
                    char letter;
                    letter = (char) Integer.parseInt(temp.substring(0, 4), 16); // 16进制parse整形字符串�??
                    ret.append(new Character(letter).toString());
                    // 剩下的字符
                    if (temp.length() > 4) {
                        ret.append(temp.substring(4));
                    }
                }
            }

        }
        return ret.toString();
    }

    /**
     * 线程休眠.优雅�?�?,不用每次都try
     */
    public static void sleeps(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("休眠出错：" + e.getMessage());
        }
    }


    /**
     * 执行CMD命令,并返回String字符�?
     */
    public static String executeCmd(String strCmd) {
        Process p;
        StringBuilder sbCmd = new StringBuilder();
        try {
            p = Runtime.getRuntime().exec("cmd /c " + strCmd);


            BufferedReader br = new BufferedReader(new InputStreamReader(p
                    .getInputStream(), "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                sbCmd.append(line + "\n");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("调用出错：" + e.getMessage());
        }


        return sbCmd.toString();
    }

    /**
     * 建立 �?个宽带连�?
     *
     * @param adslTitle 宽带连接的铝�?
     * @param adslName  宽带连接的账�?
     * @param adslPass  宽带连接的密�?
     * @return
     */
    public static String connAdsl(String adslTitle, String adslName, String adslPass) {
        System.out.println("正在建立连接.");
        String adslCmd = "rasdial " + adslTitle + " " + adslName + " "
                + adslPass;
        String tempCmd = executeCmd(adslCmd);
        // 判断是否连接成功  
        if (tempCmd.indexOf("已连�?") > 0) {
            System.out.println("已成功建立连�?.");

        } else {
            System.err.println(tempCmd);
            System.err.println("建立连接失败");

        }
        return tempCmd;
    }

    /**
     * 断开adsl拨号
     *
     * @param adslTitle 宽带连接的名�?
     * @return
     */
    public static boolean cutAdsl(String adslTitle) {
        String cutAdsl = "rasdial " + adslTitle + " /disconnect";
        String result = executeCmd(cutAdsl);

        if (result.indexOf("没有连接") != -1) {
            System.out.println(adslTitle + "连接不存在?!");
            return false;
        } else {
            System.out.println("连接已断开.");
            return true;
        }
    }


    /**
     * 如果字符串为null返回空字符串。
     *
     * @param data
     * @return
     */
    public static String trimNull(String data) {
        return data == null ? "" : data;
    }



    /**
     * 把网页上取到的timestamp转换为日期格式
     *
     * @param timestamp
     * @return
     */
    public static String timestampToDate(String timestamp) {

        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            //方法一
            Date date = new Date(Long.valueOf(timestamp));
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
     * 根据指定格式返回时间。
     *
     * @param format 格式：yyyy/MM/dd HH:mm:ss
     * @return
     */
    public static String getDate(String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date currentTime = new Date();//得到当前系统时间
        String date = formatter.format(currentTime); //将日期时间格式化
        //System.out.println(date);
        return date;
    }


    // &#开头的编码换转成中文
    public static String decodeHTML(String str) {
        String[] tmp = str.split(";&#|&#|;");
        //System.out.println(Arrays.toString(tmp));
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].matches("\\d{5}")) {
                sb.append((char) Integer.parseInt(tmp[i]));
                // System.out.println(sb.toString());
            } else {
                sb.append(tmp[i]);
            }
        }
        return sb.toString();
    }

    // 字符串转&#编码
    public static String strToHTML(String str) {
        char[] tmp = str.toCharArray();
        //System.out.println(Arrays.toString(tmp));
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < tmp.length; i++) {
            sb.append("&#").append((int) tmp[i]).append(";");
        }
        return sb.toString();
    }

    /**
     * 删除指定文件
     * @param fileName
     */
    public static void deleteFile(String fileName){
        try{
            File f=new File(fileName);
            f.getAbsoluteFile().delete();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
