package com.baby.opusfront.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Boolean isUploaded(String md5);

    String uploadBlock(String fileName, String fileMd5, Long blockSize, Long fileSize, Integer chunks, Integer chunk, MultipartFile file);
}
