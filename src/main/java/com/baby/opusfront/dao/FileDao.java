package com.baby.opusfront.dao;


import com.baby.opusfront.model.MyFile;

import java.util.Date;

//todo @Mapper
public interface FileDao {
    MyFile getFile(String fileMd5);


    void save(MyFile newFile);
}
