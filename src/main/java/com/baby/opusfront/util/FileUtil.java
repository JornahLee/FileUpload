package com.baby.opusfront.util;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    public static Map<String,Integer> chunkCount=new HashMap<>();
    public static String generatePath(String fileMd5, String rootPath, int pathLevel) {
        StringBuilder resPath=new StringBuilder();
        //每级最多有16*16=256个目录
        //三级： 总共有 256*256*256个目录
        //每级目录可以有几百个文件，所以可以保证文件比较均匀的分配在目录中。
        for(int i=0;i<pathLevel;i++){
            resPath.append(fileMd5.substring(i,i+2));
            if(i!=pathLevel-1){
                resPath.append(File.separator);
            }
        }
        resPath.insert(0,File.separator);
        resPath.insert(0,rootPath);
        return resPath.toString();
    }
    public static String getFileSuffix(String filename){
        return filename.substring(filename.lastIndexOf("."),filename.length());
    }

    public static String writeFileBlock(String fileName, Long fileSize, Long blockSize, Integer chunk, MultipartFile blockFile) throws IOException {
        File file = new File(fileName);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.seek(randomAccessFile.length()+500);
        randomAccessFile.write("阿萨德".getBytes());
        return "";
    }

}
