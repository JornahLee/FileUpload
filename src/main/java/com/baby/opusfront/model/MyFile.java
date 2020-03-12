package com.baby.opusfront.model;

import lombok.Data;

import java.util.Date;

@Data
public class MyFile {
    public static final byte UPLOAD_SUCCESS=1;
    public static final byte UPLOADING=0;
    public static final byte MISS_BLOCK=-1;
    private String fileName;//这个fileName 对于不同的user 名字不一样
    private String physicFileName;
    private Long fileSize;
    private Date uploadDt;
    private String md5;
    private byte fileStatus;
    private String path;//存放在文件系统的路径
    private String fileType;

}
