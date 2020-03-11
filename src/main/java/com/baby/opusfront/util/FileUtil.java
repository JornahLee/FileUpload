package com.baby.opusfront.util;

import ch.qos.logback.classic.spi.LoggerContextVO;
import lombok.Data;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileUtil {
    public static Map<String, Set<Integer>> chunkCount = new HashMap<>();

    public static String generatePath(String fileMd5, String rootPath, int pathLevel) {
        StringBuilder resPath = new StringBuilder();
        //每级最多有16*16=256个目录
        //三级： 总共有 256*256*256个目录
        //每级目录可以有几百个文件，所以可以保证文件比较均匀的分配在目录中。
        for (int i = 0; i < pathLevel; i++) {
            resPath.append(fileMd5.substring(i, i + 2));
            if (i != pathLevel - 1) {
                resPath.append(File.separator);
            }
        }
        resPath.insert(0, File.separator);
        resPath.insert(0, rootPath);
        return resPath.toString();
    }

    public static String getFileSuffix(String filename) {
        System.out.println("--lcg---   "+filename+"     -------");
        return filename.substring(filename.lastIndexOf("."), filename.length());
    }

    public static String writeFileBlock(String fileName, Long fileSize, Long blockSize, Integer chunk, InputStream ins) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")){
            randomAccessFile.setLength(fileSize);
            randomAccessFile.seek(blockSize * chunk);
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = ins.read(buff)) != -1) {
                randomAccessFile.write(buff, 0, len);
            }
        }

        return "";
    }

}
