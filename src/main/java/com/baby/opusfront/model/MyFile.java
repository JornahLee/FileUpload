package com.baby.opusfront.model;

import lombok.Data;

import java.util.Date;

@Data
public class MyFile {
    private String fileId;
    private String fileName;
    private Long fileSize;
    private Date uploadDt;
    private String md5;
    private Integer statusCd;
    private String path;
    private String suffix;

}
