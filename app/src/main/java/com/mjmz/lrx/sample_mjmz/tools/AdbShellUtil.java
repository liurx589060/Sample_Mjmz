package com.mjmz.lrx.sample_mjmz.tools;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/11/11.
 */

public class AdbShellUtil {
    private Process p;

    private static class AdbShellUtilHolder {
        private static final AdbShellUtil INSTANCE = new AdbShellUtil();
    }

    private AdbShellUtil() {
    }

    public Process initProcess() {
        if(p == null) {
            try {
                p = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("yy","创建Process失败--" + e.getMessage());
            }
        }
        return p;
    }

    public static AdbShellUtil getInstance() {
        return AdbShellUtilHolder.INSTANCE;
    }

    /**
     * 执行Shell命令
     *
     * @param commands
     *            要执行的命令数组
     */
    public void execShell(String[] commands,boolean isRoot) {
        // 获取Runtime对象
        Runtime runtime = Runtime.getRuntime();

        DataOutputStream os = null;
        try {
            // 获取root权限，这里大量申请root权限会导致应用卡死，可以把Runtime和Process放在Application中初始化
            Process process = runtime.exec(isRoot?"su":"sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                Log.e("yy","cmd=" + command);
                // donnot use os.writeBytes(commmand), avoid chinese charset
                // error
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
            os.close();
//                process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("yy","执行shell失败--" + e.toString());
        }
    }

    /**
     * 把中文转成Unicode码
     * @param str
     * @return
     */
    public String chineseToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为中文字符
     * @param c
     * @return
     */
    public  boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
