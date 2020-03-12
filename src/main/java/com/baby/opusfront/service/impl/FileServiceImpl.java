package com.baby.opusfront.service.impl;


import com.baby.opusfront.dao.FileDao;
import com.baby.opusfront.model.MyFile;
import com.baby.opusfront.service.FileService;
import com.baby.opusfront.util.FileUtil;
import com.baby.opusfront.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.rootPath}")
    private String rootPath;

    @Value("${file.pathLevel}")
    private Integer pathLevel;

    @Autowired
    private FileDao fileDao;

    @Override
    public Boolean isUploaded(String md5) {
        return false;
    }

    @Override
    public String uploadBlock(String fileName, String fileMd5, Long blockSize, Long fileSize, Integer chunks, Integer chunk, MultipartFile blockFile) {
        MyFile myFile = fileDao.getFile(fileMd5);
        if (myFile == null) {
            myFile=new MyFile();
            myFile.setMd5(fileMd5);
            String path = FileUtil.generatePath(fileMd5, rootPath, pathLevel);
            myFile.setPath(path);
            myFile.setFileType(FileUtil.getFileSuffix(fileName));
            String physicFileName = path +
                    File.separator +
                    myFile.getMd5() +
                    myFile.getFileType();
            myFile.setPhysicFileName(physicFileName);
            myFile.setFileName(blockFile.getOriginalFilename());
        }
        try {
            System.out.println("--lcg---   "+myFile.getPhysicFileName()+"     -------");
            FileUtil.writeFileBlock(myFile.getPhysicFileName(), fileSize, blockSize, chunk, blockFile.getInputStream());
            Set<Integer> chunkSet = FileUtil.chunkCount.get(fileMd5);
            if (chunkSet == null) {
                chunkSet = new HashSet<>();
                myFile.setFileSize(fileSize);
                FileUtil.chunkCount.put(fileMd5,chunkSet);
                fileDao.save(myFile);
            } else if (chunkSet.size() == chunks-1) {
                myFile.setFileStatus(MyFile.UPLOAD_SUCCESS);
                //check md5
                String md5Verify = MD5Util.getFileMD5(new File(myFile.getPhysicFileName()));
                if(md5Verify!=null && md5Verify.equals(fileMd5)){
                    FileUtil.chunkCount.remove(myFile.getMd5());
                    return "文件全部上传完成";
                }
            }
            chunkSet.add(chunk);
            myFile.setUploadDt(new Date());
            myFile.setFileStatus(MyFile.UPLOADING);
            fileDao.updateUploadStatus(fileMd5, myFile);

        } catch (IOException e) {
            e.printStackTrace();
            return "上传 块"+chunk+" 出错";
        }
        return "上传 块"+chunk+" 成功";
    }

    public static void main(String[] args) throws IOException {
        short a=1+1;
        byte b=127;
        useRandomAccessFile();
    }

    public static void useRandomAccessFile() throws IOException {
        String filename = "asdas.txt";
        File file = new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }
        String fileMD5 = MD5Util.getFileMD5(file);
        System.out.println(fileMD5.length()+"::"+fileMD5);
        Integer.valueOf("");
        new HashMap().v;
        Integer.parseInt("");

    }
}
