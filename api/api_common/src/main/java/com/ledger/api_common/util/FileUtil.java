package com.ledger.api_common.util;

import com.ledger.api_common.Exception.KnowException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static String copeFile(MultipartFile file, String path, String pre) {
        if (file == null) {
            throw new KnowException("文件为空");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(path + "." + fileExtension);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.close();
                return pre + path + "." + fileExtension;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
