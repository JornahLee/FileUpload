package com.baby.opusfront.service.impl;


import com.baby.opusfront.dao.FileDao;
import com.baby.opusfront.model.MyFile;
import com.baby.opusfront.service.FileService;
import com.baby.opusfront.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("file.rootPath")
    private String rootPath;

    @Value("file.pathLevel")
    private int pathLevel;

    @Autowired
    private FileDao fileDao;

    @Override
    public Boolean isUploaded(String md5) {
        return null;
    }

    @Override
    public String uploadBlock(String fileMd5, Long blockSize, Long fileSize, Integer chunks, Integer chunk, MultipartFile blockFile) {

        MyFile newFile = new MyFile();
        newFile.setFileName(blockFile.getOriginalFilename());
        newFile.setFileSize(fileSize);
        newFile.setStatusCd(0);
        newFile.setUploadDt(new Date());
        newFile.setSuffix(FileUtil.getFileSuffix(blockFile.getOriginalFilename()));
        try {
            String fileId = UUID.randomUUID().toString();
            String path = FileUtil.generatePath(fileMd5, rootPath, pathLevel);
            String fileName = path +
                    File.separator +
                    newFile.getFileId() +
                    newFile.getSuffix();
            FileUtil.writeFileBlock(fileName, fileSize, blockSize, chunk, blockFile);
            Integer chunkCount = FileUtil.chunkCount.get(fileMd5);
            if(chunkCount==null){
                FileUtil.chunkCount.put(fileMd5,1);
            }else{
                FileUtil.chunkCount.put(fileMd5,++chunkCount);
            }
            //如何判断是否已经上传完毕？
            if(FileUtil.chunkCount.get(fileMd5)==chunks){
                //传输完成
                newFile.setStatusCd(1);
            }
            newFile.setFileId(fileId);
            newFile.setPath(path);
            // 查db  得到fileId，然后在面进行追加写文件
            MyFile myFile = fileDao.getFile(fileMd5);
            // FileUtil.amendFile(myFile.getFileId(), myFile.getPath(), chunk, blockSize, blockFile);
            //update blockFile Info in db
            fileDao.save(newFile);

        } catch (IOException e) {
            e.printStackTrace();
            return "uploadBlock error";
        }
        return "uploadBlock suceess";
    }

    public static void main(String[] args) throws IOException {
        useRandomAccessFile();
    }

    public static void useRandomAccessFile() throws IOException {
        String filename = "asdas.txt";
        String suffix = FileUtil.getFileSuffix(filename);
        System.out.println("--licg---   suffix   : " + suffix + "    -----");

    }
}
