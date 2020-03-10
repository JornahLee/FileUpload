package com.baby.opusfront.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
// @RunWith
@RunWith(SpringRunner.class)
public class FileServiceImplTest {

    @Autowired
    private FileServiceImpl fs;

    @Test
    public void testUploadBlock() {
        Long blockSize=4L;// byte
        int chunks=5;
        Integer chunk=0;
        byte[] fileContent=new byte[blockSize.intValue()];
        while(chunk<5){
            for (int i = 0; i < fileContent.length; i++) {
                fileContent[i]=String.valueOf(chunk).getBytes()[0];
            }
            String fileName="asdf.txt";
            fs.uploadBlock(fileName, "123123142bb",blockSize,chunks*blockSize,chunks,chunk,new MockMultipartFile("block1.txt",fileContent));
            chunk++;
        }
    }


}