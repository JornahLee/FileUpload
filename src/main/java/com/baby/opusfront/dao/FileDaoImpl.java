package com.baby.opusfront.dao;

import com.baby.opusfront.model.MyFile;
import org.springframework.stereotype.Repository;

@Repository
public class FileDaoImpl implements FileDao {
    @Override
    public MyFile getFile(String fileMd5) {
        return null;
    }

    @Override
    public void save(MyFile newFile) {

    }

    @Override
    public void updateUploadStatus(String fileMd5, MyFile uploadStatus) {

    }
}
