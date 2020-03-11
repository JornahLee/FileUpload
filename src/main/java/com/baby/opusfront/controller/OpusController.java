package com.baby.opusfront.controller;


import com.baby.opusfront.model.MyFile;
import com.baby.opusfront.service.FileService;
import com.baby.opusfront.util.FileUtil;
import com.baby.opusfront.util.ShellCaller;
import com.baby.opusfront.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
public class OpusController {


    @Value("${baby.opus.path}")
    private String path;

    @Autowired
    private FileService fileService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping("/upload")
    @ResponseBody
    public String uploadOpus(@RequestParam("file") MultipartFile upload) {
        System.out.println("springmvc文件上传...");
        String filename = upload.getOriginalFilename();
        int sepIndex = filename.lastIndexOf(".");
        String suffix = filename.substring(sepIndex + 1);
        if (!"zip".equals(suffix)) {
            return "仅支持 作业打包为 zip";
        }
        String saveName = filename.substring(0, sepIndex) + "." + suffix;
        File newFile = null;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            newFile = new File(path, saveName);
            if (newFile.exists()) {
                return "已经上传过 " + saveName + " 了";
            }
            upload.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传   失败";
        }

        try {
            ZipUtil.unZipFiles(path + "/" + saveName, path);
            newFile.delete();
            checkFileName(newFile);
        } catch (IOException e) {
            newFile.delete();
            e.printStackTrace();
            return "解压错误";
        }

        return "文件上传   成功<a href='http ://118.25.187.1:1010'>点击查看</a>";
    }

    public static void main(String[] args) throws IOException {
        String filename = "D:\\usr\\jornahless\\opus-front\\2020-03-02";
        System.out.println("--licg---      : " + new File(filename).getParent() + "    -----");
        System.out.println("--licg---     File.pathSeparator : " + File.separator + "    -----");
        checkFileName(new File(filename));
    }

    public static void checkFileName(File file) throws IOException {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                checkFileName(listFile);
            }
        } else {
            if ("index.html".equals(file.getName())) {
                FileCopyUtils.copy(file, new File(file.getParent() + File.separator + "index-rn.html"));
                file.delete();
            }
        }
    }

    @RequestMapping("/uploadBlock")
    @ResponseBody
    public String uploadBlock(
            String fileName,
            String fileMd5,
            Long fileSize,
            Long blockSize,
            Integer chunks,
            Integer chunk,
            MultipartFile blockFile) throws IOException {
        //是否存在
        if (fileService.isUploaded(fileMd5)) {
            return "文件已上传完成";
        } else {

            return fileService.uploadBlock(fileName, fileMd5, blockSize, fileSize, chunks, chunk, blockFile);
        }

    }


}
