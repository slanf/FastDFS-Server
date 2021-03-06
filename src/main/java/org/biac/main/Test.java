package org.biac.main;

import org.biac.bean.json.UploadDownJson;
import org.biac.bean.packet.BasePackage;
import org.biac.exception.CrcCheckErrorException;
import org.biac.exception.IllegalPackageException;
import org.biac.service.Handler;
import org.biac.tool.JsonTool;
import org.biac.tool.PacketTool;

import java.io.*;

/**
 * Created by zhangyuanan on 16/1/4.
 */
public class Test {
    public static void main(String[] args) {


        byte[] requestBytes = new byte[0];
        try {
            requestBytes = toByteArray("E:/Test/upload.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BasePackage basePackage = new BasePackage(requestBytes);
        int statusCode;
        String errorMsg;
        try {
            basePackage.check();
            basePackage.prepare();
        } catch (IllegalPackageException e) {
            statusCode = 1;
            errorMsg = e.getMessage();
            e.printStackTrace();
        } catch (CrcCheckErrorException e) {
            statusCode = 2;
            errorMsg = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            statusCode = 3;
            errorMsg = e.getMessage();
            e.printStackTrace();
            String jsonString = JsonTool.toJson(new UploadDownJson(statusCode,errorMsg));
            byte[] respContent = PacketTool.pack(basePackage.getCopeCode(),jsonString,null);
        }
        Handler handler = basePackage.selectHandler();
        handler.handle();
        byte[] respContent = PacketTool.pack(basePackage.getCopeCode(),handler.getJsonString(),handler.getContent());
    }

    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
}
