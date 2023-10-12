package com.ledger.api_common.util;

import com.ledger.api_common.Exception.KnowException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileUtil {

    public static String uploadFile(MultipartFile file, String name, String profile) {
        if (file == null) {
            throw new KnowException("文件为空");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(profile + name + "." + fileExtension);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();
                return name + "." + fileExtension;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    public static byte[] downloadFile(String filename, String profile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(profile + filename);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) > 0) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
            // 获取完整的字节数组
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String byteToBase64(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

}
